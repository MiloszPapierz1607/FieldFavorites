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
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewDestination
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewScreen
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
                    canNavigateBack = favoriteUiState.favoriteTeams.isNotEmpty(),
                    navigateToTeams = {
                        navController.navigate("${TeamsDestination.route}/${it}")
                    },
                    navigateToFavorites = {
                        navController.navigate(FavoritesDestination.route)
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
                    favoriteTeamsIds = favoriteUiState.favoriteTeams.map { it.id },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = FavoritesDestination.route
            ) {
                FavoritesScreen(
                    navigateToOverviewScreen = { teamId,teamName ->
                       navController.navigate("${TeamOverviewDestination.route}/$teamId/$teamName")
                    },
                navigateToLeagueScreen = {
                    navController.navigate(LeaguesDestination.route)
                },
                favoriteViewModel = favoriteViewModel
                )
            }

            composable(
                route = TeamOverviewDestination.routeWithArgs,
                arguments = listOf(
                    navArgument(TeamOverviewDestination.itemIdArg) { type = NavType.IntType },
                    navArgument(TeamOverviewDestination.itemNameArg) {type = NavType.StringType}
                )
            ) {
                TeamOverviewScreen()
            }
        }
    }
}