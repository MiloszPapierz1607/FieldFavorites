package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.model.Standings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Ui State for TeamOverviewScreen
 * */
data class TeamOverviewUiState(
    val nextFixture: FixtureRow? = null,
    val standings: List<Standings> = listOf(),
    val playerStats: List<PlayerRow> = listOf()
)

/**
 * Interface that holds the api state for TeamOverviewScreen
 * */
sealed interface TeamOverviewApiState {
    data class Success(
        val nextFixture: FixtureRow,
        val standings: List<Standings>,
        val playerStats: List<PlayerRow>
    ) :
        TeamOverviewApiState

    object Loading : TeamOverviewApiState
    object Error : TeamOverviewApiState
}

/**
 * ViewModel to retrieve all items from the repositories to show the overview of a team
 * */
class TeamOverviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamOverviewRepository: TeamOverviewRepository,
    private val standingsRepository: StandingsRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _teamId: Int = savedStateHandle[TeamOverviewScreenDestination.itemIdArg] ?: 0
    val teamId: Int get() = _teamId
    private val _teamName: String =
        savedStateHandle[TeamOverviewScreenDestination.itemNameArg] ?: ""
    val teamName: String get() = _teamName

    private val _uiState = MutableStateFlow(TeamOverviewUiState())

    /**
     * Holds [TeamOverviewUiState] for the [TeamOverviewScreen]
     * */
    val uiState: StateFlow<TeamOverviewUiState> = _uiState.asStateFlow()

    /**
     * Holds [TeamOverviewApiState] for the [TeamOverviewScreen]
     * */
    var teamOverviewApiState: TeamOverviewApiState by mutableStateOf(TeamOverviewApiState.Loading)
        private set

    init {
        getInitialData()
    }

    /**
     * A function that fetches initial data such as the next upcoming [Standings], [List] of [PlayerRow] and [FixtureRow] using
     * [StandingsRepository], [TeamOverviewRepository] and [FavoriteRepository].
     * If the leagueId in the viewmodel equals 0 a [Exception] is thrown
     * */
    private fun getInitialData() {
        viewModelScope.launch {
            try {
                val firstFixture = teamOverviewRepository.getFixturesByTeamId(_teamId, 1)[0]
                val leagueId = favoriteRepository.getTeamById(_teamId)?.leagueId ?: 0

                if (leagueId == 0) throw Exception("Could not get a team with given id from database")

                val standings = standingsRepository.getStandingsForLeague(leagueId)

                val playerStats = teamOverviewRepository.getPlayersByTeamId(_teamId)

                _uiState.update {
                    it.copy(
                        nextFixture = firstFixture,
                        standings = standings,
                        playerStats = playerStats
                    )
                }

                teamOverviewApiState =
                    TeamOverviewApiState.Success(firstFixture, standings, playerStats)
            } catch (e: Exception) {
                teamOverviewApiState = TeamOverviewApiState.Error
            }
        }
    }
}