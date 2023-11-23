package com.example.fieldfavorites.ui.screens.leagues

import androidx.lifecycle.ViewModel
import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Ui State for [LeaguesScreen]
 * */
sealed interface LeagueUiState {
    data class Success(val leagues: List<League>) : LeagueUiState
}

/**
 * ViewModel to retrieve all [League] from a [LeagueRepository]
 * */
class LeagueViewModel(
    leagueRepository : LeagueRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LeagueUiState.Success(leagueRepository.getTop5Leagues()))
    /**
     * Holds [LeagueUiState] fetched from the [LeagueRepository].
     * */
    val uiState: StateFlow<LeagueUiState.Success> = _uiState.asStateFlow()

}