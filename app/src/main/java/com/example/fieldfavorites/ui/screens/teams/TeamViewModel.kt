package com.example.fieldfavorites.ui.screens.teams

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.model.League
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TeamUiState(
    val teams: List<Team> = listOf()
)

class TeamViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamsRepository: TeamRepository
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[TeamsDestination.itemIdArg])
    private val _uiState = MutableStateFlow(TeamUiState())
    val uiState: StateFlow<TeamUiState> = _uiState.asStateFlow()

    init {
        getTeams()
    }

    fun getTeams() {
        viewModelScope.launch {
            val teams = teamsRepository.getAllTeamsFromLeague(itemId)
            println(teams.size)
            _uiState.update { currentState ->
                currentState.copy(
                    teams = teams
                )
            }
        }
    }
}