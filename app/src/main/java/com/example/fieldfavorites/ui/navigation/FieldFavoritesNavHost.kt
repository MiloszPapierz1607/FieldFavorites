package com.example.fieldfavorites.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fieldfavorites.ui.screens.leagues.LeaguesDestination
import com.example.fieldfavorites.ui.screens.leagues.LeaguesScreen
import com.example.fieldfavorites.ui.screens.teams.TeamsDestination
import com.example.fieldfavorites.ui.screens.teams.TeamsScreen

@Composable
fun FieldFavoritesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LeaguesDestination.route,
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
    }
}