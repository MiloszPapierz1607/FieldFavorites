package com.example.fieldfavorites.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoriteTeamsUiState(
    val favoriteTeams: List<Team> = listOf(),
    val isLoading: Boolean = true
)

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) :ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteTeamsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFavoriteTeams()
    }

     private fun fetchFavoriteTeams() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteTeamsStream().collect {
                _uiState.value = FavoriteTeamsUiState(favoriteTeams = it,isLoading = false)
            }
        }
    }
}