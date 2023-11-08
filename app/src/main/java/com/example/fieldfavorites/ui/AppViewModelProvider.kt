package com.example.fieldfavorites.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fieldfavorites.FieldFavoritesApplication
import com.example.fieldfavorites.ui.screens.leagues.LeagueViewModel
import com.example.fieldfavorites.ui.screens.teams.TeamViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LeagueViewModel(fieldFavoritesApplication().container.leagueRepository)
        }

        initializer {
            TeamViewModel(this.createSavedStateHandle(),fieldFavoritesApplication().container.teamRepository)
        }
    }
}

fun CreationExtras.fieldFavoritesApplication(): FieldFavoritesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FieldFavoritesApplication)