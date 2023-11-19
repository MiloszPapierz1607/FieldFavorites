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
import java.io.IOException

data class TeamUiState(
    val teams: List<Team> = listOf()
)

sealed interface TeamApiState {
    data class Success(val teams: List<Team>) : TeamApiState
    object Loading : TeamApiState
    object Error : TeamApiState
}

class TeamViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamsRepository: TeamRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _itemId: Int = checkNotNull(savedStateHandle[TeamsDestination.itemIdArg])
    private val _uiState = MutableStateFlow(TeamUiState())
    val uiState: StateFlow<TeamUiState> = _uiState.asStateFlow()

    var teamApiState: TeamApiState by mutableStateOf(TeamApiState.Loading)
        private set

    init {
        getTeams()
    }

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
            catch (e: IOException) {
                teamApiState = TeamApiState.Error
            }
        }
    }

    fun insertFavoriteTeam(team: Team) {
        viewModelScope.launch {
            favoriteRepository.insertFavoriteTeam(team)
        }
    }
}