package com.example.fieldfavorites.ui.screens.teams

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamsDestination : NavigationDestination {
    override val route = "teams"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun TeamsScreen() {
    Text("Leagues screen")
}