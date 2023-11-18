package com.example.fieldfavorites.ui.screens.teamoverview.players

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamOverviewPlayersScrenDestination : NavigationDestination {
    override val route: String = "teamoverviewplayers"
}

@Composable
fun TeamOverviewPlayersScreen() {
    Text("TEST")
}