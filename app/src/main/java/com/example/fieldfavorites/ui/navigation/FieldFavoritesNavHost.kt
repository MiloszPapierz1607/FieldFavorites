package com.example.fieldfavorites.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.screens.favorites.FavoriteViewModel
import com.example.fieldfavorites.ui.screens.favorites.FavoritesDestination
import com.example.fieldfavorites.ui.screens.favorites.FavoritesScreen
import com.example.fieldfavorites.ui.screens.leagues.LeaguesDestination
import com.example.fieldfavorites.ui.screens.leagues.LeaguesScreen
import com.example.fieldfavorites.ui.screens.teams.TeamsDestination
import com.example.fieldfavorites.ui.screens.teams.TeamsScreen

@Composable
fun FieldFavoritesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val favoriteUiState by favoriteViewModel.uiState.collectAsState()

    if(!favoriteUiState.isLoading) {
        NavHost(
            navController = navController,
            startDestination = if (favoriteUiState.favoriteTeams.isEmpty()) LeaguesDestination.route else FavoritesDestination.route,
            modifier = modifier
        ) {

            composable(route = LeaguesDestination.route) {
                LeaguesScreen(
                    navigateToTeams = {
                        navController.navigate("${TeamsDestination.route}/${it}")
                    }
                )
            }

            composable(
                route = TeamsDestination.routeWithArgs,
                arguments = listOf(navArgument(TeamsDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                TeamsScreen(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = FavoritesDestination.route
            ) {
                FavoritesScreen(favoriteViewModel = favoriteViewModel)
            }
        }
    }
}