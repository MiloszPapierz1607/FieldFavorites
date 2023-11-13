package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class TeamOverviewViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val teamId: Int = checkNotNull(savedStateHandle[TeamOverviewDestination.itemIdArg])

    init {
        println(teamId)
    }
}