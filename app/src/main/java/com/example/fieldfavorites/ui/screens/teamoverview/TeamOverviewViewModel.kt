package com.example.fieldfavorites.ui.screens.teamoverview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.model.FixtureRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TeamOverviewUiState(
    val nextFixture: FixtureRow? = null
)

sealed interface TeamOverviewApiState {
    data class Success(val nextFixture: FixtureRow) : TeamOverviewApiState
    object Loading : TeamOverviewApiState
    object Error : TeamOverviewApiState
}

class TeamOverviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val teamOverviewRepository: TeamOverviewRepository
) : ViewModel() {
    private val _teamId: Int = checkNotNull(savedStateHandle[TeamOverviewDestination.itemIdArg])
    val teamId: Int get() = _teamId
    private val _teamName: String = savedStateHandle[TeamOverviewDestination.itemNameArg] ?: ""
    val teamName: String get() = _teamName

    private val _uiState = MutableStateFlow(TeamOverviewUiState())
    val uiState: StateFlow<TeamOverviewUiState> = _uiState.asStateFlow()

    var teamOverviewApiState: TeamOverviewApiState by mutableStateOf(TeamOverviewApiState.Loading)
        private set

    init {
        getFixtures()
    }

    private fun getFixtures() {
        viewModelScope.launch {
            try {
                val firstFixture = teamOverviewRepository.getFixturesByTeamId(_teamId,1)[0]

                _uiState.update {
                    it.copy(nextFixture = firstFixture)
                }

                teamOverviewApiState = TeamOverviewApiState.Success(firstFixture)
            } catch (e: Exception) {
                Log.v("Test",e.message.toString())
                teamOverviewApiState = TeamOverviewApiState.Error
            }
        }
    }
}