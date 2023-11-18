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
import com.example.fieldfavorites.model.Standings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TeamOverviewUiState(
    val nextFixture: FixtureRow? = null,
    val standings: List<Standings> = listOf()
)

sealed interface TeamOverviewApiState {
    data class Success(val nextFixture: FixtureRow,val standings: List<Standings>) :
        TeamOverviewApiState
    object Loading : TeamOverviewApiState
    object Error : TeamOverviewApiState
}

class TeamOverviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamOverviewRepository: TeamOverviewRepository,
    private val standingsRepository: StandingsRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _teamId: Int = checkNotNull(savedStateHandle[TeamOverviewScreenDestination.itemIdArg])
    val teamId: Int get() = _teamId
    private val _teamName: String = savedStateHandle[TeamOverviewScreenDestination.itemNameArg] ?: ""
    val teamName: String get() = _teamName

    private val _uiState = MutableStateFlow(TeamOverviewUiState())
    val uiState: StateFlow<TeamOverviewUiState> = _uiState.asStateFlow()

    var teamOverviewApiState: TeamOverviewApiState by mutableStateOf(TeamOverviewApiState.Loading)
        private set

    init {
        getInitialData()
    }

    private fun getInitialData() {
        viewModelScope.launch {
            try {
                val firstFixture = teamOverviewRepository.getFixturesByTeamId(_teamId,1)[0]
                val leagueId = favoriteRepository.getTeamById(_teamId)?.leagueId ?: 0

                if(leagueId == 0) throw Exception("Could not get a team with given id from database")

                val standings = standingsRepository.getStandingsForLeague(leagueId)

                _uiState.update {
                    it.copy(nextFixture = firstFixture,standings = standings)
                }

                teamOverviewApiState = TeamOverviewApiState.Success(firstFixture, standings)
            } catch (e: Exception) {
                teamOverviewApiState = TeamOverviewApiState.Error
            }
        }
    }
}