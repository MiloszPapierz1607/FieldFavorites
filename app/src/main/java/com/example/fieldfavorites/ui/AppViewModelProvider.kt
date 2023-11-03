package com.example.fieldfavorites.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fieldfavorites.FieldFavoritesApplication
import com.example.fieldfavorites.ui.screens.leagues.LeagueViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LeagueViewModel(fieldFavoritesApplication().container.leagueRepository)
        }
    }
}

fun CreationExtras.fieldFavoritesApplication(): FieldFavoritesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FieldFavoritesApplication)