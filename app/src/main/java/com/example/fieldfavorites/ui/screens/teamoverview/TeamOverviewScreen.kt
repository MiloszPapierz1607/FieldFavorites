package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamOverviewDestination : NavigationDestination {
    override val route = "teamoverview"
    const val itemIdArg = "teamId"
    val routeWithArgs = "${route}/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamOverviewScreen(
    modifier: Modifier = Modifier,
    teamOverviewViewModel: TeamOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = "team name",
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
            Text("afda")
        }
    }
}