package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.components.LoadingComponent
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamOverviewDestination : NavigationDestination {
    override val route = "teamoverview"
    const val itemIdArg = "teamId"
    const val itemNameArg = "teamName"
    val routeWithArgs = "${route}/{$itemIdArg}/{$itemNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamOverviewScreen(
    modifier: Modifier = Modifier,
    teamOverviewViewModel: TeamOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val teamOverviewUiState by teamOverviewViewModel.uiState.collectAsState()
    val teamOverviewApiState = teamOverviewViewModel.teamOverviewApiState

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = teamOverviewViewModel.teamName,
                canNavigateBack = true
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(teamOverviewApiState) {
                is TeamOverviewApiState.Loading -> LoadingComponent()
                is TeamOverviewApiState.Error -> Text("Something went wrong. Try again later!")
                is TeamOverviewApiState.Success -> Text(teamOverviewUiState.nextFixture?.teams?.home?.name ?: "")
            }

        }
    }
}