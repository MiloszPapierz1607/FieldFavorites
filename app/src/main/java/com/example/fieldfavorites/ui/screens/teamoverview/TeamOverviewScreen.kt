package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.ActionMenuItem
import com.example.fieldfavorites.BottomAppBarItem
import com.example.fieldfavorites.DeviceType
import com.example.fieldfavorites.FieldFavoritesBottomAppBar
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.components.LoadingComponent
import com.example.fieldfavorites.ui.navigation.NavigationDestination
import com.example.fieldfavorites.ui.screens.teamoverview.home.TeamOverviewHomeScreen
import com.example.fieldfavorites.ui.screens.teamoverview.players.TeamOverviewPlayersScreen

object TeamOverviewScreenDestination : NavigationDestination {
    override val route = "teamoverview"
    const val itemIdArg = "teamId"
    const val itemNameArg = "teamName"
    val routeWithArgs = "$route/{$itemIdArg}/{$itemNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamOverviewScreen(
    deviceType: DeviceType,
    goBack: () -> Unit,
    removeFromFavorite: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    teamOverviewViewModel: TeamOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var currentScreen by rememberSaveable { mutableStateOf(TeamOverviewScreenContent.TeamOverviewHomeScreenContent) }
    val teamOverviewUiState by teamOverviewViewModel.uiState.collectAsState()
    val teamOverviewApiState = teamOverviewViewModel.teamOverviewApiState

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = teamOverviewViewModel.teamName,
                canNavigateBack = true,
                navigateUp = goBack,
                showActionsMenu = true,
                items = listOf(
                    ActionMenuItem.NeverShown(
                        title = "Remove from favorites",
                        onClick = {removeFromFavorite(teamOverviewViewModel.teamId)}
                    )
                )
            )
        },
        bottomBar = {
            FieldFavoritesBottomAppBar(
                bottomAppBarItems = listOf(
                    BottomAppBarItem.BottomAppBarItemWithLabel(Icons.Default.Home,{currentScreen = TeamOverviewScreenContent.TeamOverviewHomeScreenContent },"Home"),
                    BottomAppBarItem.BottomAppBarItemWithLabel(Icons.Default.Person,{currentScreen = TeamOverviewScreenContent.TeamOverviewPlayersScreenContent},"Players")
                )
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when(teamOverviewApiState) {
                is TeamOverviewApiState.Loading -> LoadingComponent()
                is TeamOverviewApiState.Error -> Text("Something went wrong. Try again later!")
                is TeamOverviewApiState.Success -> when(currentScreen) {
                    TeamOverviewScreenContent.TeamOverviewHomeScreenContent -> TeamOverviewHomeScreen(
                        nextFixture = teamOverviewUiState.nextFixture!!,
                        standings = teamOverviewUiState.standings
                    )
                    TeamOverviewScreenContent.TeamOverviewPlayersScreenContent -> TeamOverviewPlayersScreen(deviceType,teamOverviewUiState.playerStats)
                }
            }
        }
    }
}

enum class TeamOverviewScreenContent {
    TeamOverviewHomeScreenContent,
    TeamOverviewPlayersScreenContent
}