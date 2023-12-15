package com.example.fieldfavorites.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
import com.example.fieldfavorites.DeviceType
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.screens.favorites.FavoriteViewModel
import com.example.fieldfavorites.ui.screens.favorites.FavoritesDestination
import com.example.fieldfavorites.ui.screens.favorites.FavoritesScreen
import com.example.fieldfavorites.ui.screens.leagues.LeaguesDestination
import com.example.fieldfavorites.ui.screens.leagues.LeaguesScreen
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewScreen
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewScreenDestination
import com.example.fieldfavorites.ui.screens.teams.TeamsDestination
import com.example.fieldfavorites.ui.screens.teams.TeamsScreen

/**
 * Provides Navigation graph for the application.
 * */
@Composable
fun FieldFavoritesNavHost(
    deviceType: DeviceType,
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
                    deviceType,
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
                }),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(700)
                    )
                }
            ) {
                TeamsScreen(
                    deviceType,
                    favoriteTeamsIds = favoriteUiState.favoriteTeams.map { it.id },
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToFavorites = {
                        navController.navigate(FavoritesDestination.route)
                    }
                )
            }

            composable(
                route = FavoritesDestination.route
            ) {
                FavoritesScreen(
                    deviceType,
                    favoriteUiState.favoriteTeams,
                    navigateToOverviewScreen = { teamId,teamName ->
                       navController.navigate("${TeamOverviewScreenDestination.route}/$teamId/$teamName")
                    },
                navigateToLeagueScreen = {
                    navController.navigate(LeaguesDestination.route)
                }
                )
            }

            composable(
                route = TeamOverviewScreenDestination.routeWithArgs,
                arguments = listOf(
                    navArgument(TeamOverviewScreenDestination.itemIdArg) { type = NavType.IntType },
                    navArgument(TeamOverviewScreenDestination.itemNameArg) {type = NavType.StringType}
                ),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(700)
                    )
                }
            ) {
                TeamOverviewScreen(
                    deviceType,
                    goBack = {
                             navController.navigate(FavoritesDestination.route)
                    },
                    removeFromFavorite = {
                        favoriteViewModel.removeFavoriteTeam(it)
                        navController.navigate(if (favoriteUiState.favoriteTeams.isEmpty()) LeaguesDestination.route else FavoritesDestination.route)
                    }
                )
            }

        }
    }
}