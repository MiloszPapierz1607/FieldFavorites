package com.example.fieldfavorites.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Ui State for [FavoritesScreen]
 * */
data class FavoriteTeamsUiState(
    val favoriteTeams: List<Team> = listOf(),
    val isLoading: Boolean = true
)

/**
 * ViewModel to retrieve all favorite teams from the Room database.
 * */
class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) :ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteTeamsUiState())
    /**
     * Holds favorites ui state. The list of items is retrieved from [FavoriteRepository].
     * */
    val uiState = _uiState.asStateFlow()

    init {
        fetchFavoriteTeams()
    }

    /**
     * Fetches all the favorite teams of the user from [FavoriteRepository] data source.
     * */
     private fun fetchFavoriteTeams() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteTeamsStream().collect {
                _uiState.value = FavoriteTeamsUiState(favoriteTeams = it,isLoading = false)
            }
        }
    }

    /**
     * Removes a [Team] from the [FavoriteRepository] data source using the given [id] of the [Team] that
     * has to be deleted.
     * */
    fun removeFavoriteTeam(id: Int) {
        viewModelScope.launch {
            favoriteRepository.deleteTeam(id)
        }
    }
}