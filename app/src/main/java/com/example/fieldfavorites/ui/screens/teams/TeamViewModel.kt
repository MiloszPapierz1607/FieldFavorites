package com.example.fieldfavorites.ui.screens.teams

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Ui State for [TeamsScreen]
 * */
data class TeamUiState(
    val teams: List<Team> = listOf()
)

/**
 * Interface that holds the api state for [TeamsScreen]
 * */
sealed interface TeamApiState {
    data class Success(val teams: List<Team>) : TeamApiState
    object Loading : TeamApiState
    object Error : TeamApiState
}

/**
 * ViewModel to retrieve all teams for a league using the given repositories
 * */
class TeamViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamsRepository: TeamRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    /**
     * Holds the id of a league the user is currently on.
     * */
    private val _itemId: Int = savedStateHandle[TeamsDestination.itemIdArg] ?: 0
    private val _uiState = MutableStateFlow(TeamUiState())
    /**
     * Holds [TeamUiState] for [TeamsScreen]
     * */
    val uiState: StateFlow<TeamUiState> = _uiState.asStateFlow()

    /**
     * Holds [TeamApiState] for [TeamsScreen]
     * */
    var teamApiState: TeamApiState by mutableStateOf(TeamApiState.Loading)
        private set

    init {
        getTeams()
    }

    /**
     * Fetches the teams from [TeamRepository] data source for a specific league.
     * */
    private fun getTeams() {
        viewModelScope.launch {
            try {
                val teams = teamsRepository.getAllTeamsFromLeague(_itemId)
                    .sortedBy {
                        it.name
                    }

                _uiState.update { currentState ->
                    currentState.copy(
                        teams = teams
                    )
                }

                teamApiState = TeamApiState.Success(teams)
            }
            catch (e: Exception) {
                teamApiState = TeamApiState.Error
            }
        }
    }

    /**
     * Inserts a [Team] to the [FavoriteRepository] data source.
     * */
    fun insertFavoriteTeam(team: Team) {
        viewModelScope.launch {
            favoriteRepository.insertFavoriteTeam(team)
        }
    }
}