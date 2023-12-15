package com.example.fieldfavorites

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.fieldfavorites.ui.screens.favorites.FavoritesDestination
import com.example.fieldfavorites.ui.screens.leagues.LeaguesDestination
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController:TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            FieldFavoritesApp(deviceType = DeviceType.MOBILE,navController)
        }
    }


    @Test
    fun verifyStartDestination()  {
        Assert.assertEquals(LeaguesDestination.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navigateToTeamsScreen() {
        composeTestRule
            .onNodeWithText("Choose a league")
        composeTestRule
            .onAllNodesWithTag("leagueCard")
            .onFirst()
            .performClick()
        composeTestRule
            .onAllNodesWithTag("teamCard")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun canLookAtTeamOverviewAfterAddingTeamToFavorite() {
        composeTestRule
            .onNodeWithText("Choose a league")
        composeTestRule
            .onAllNodesWithTag("leagueCard")
            .onFirst()
            .performClick()
        composeTestRule
            .onAllNodesWithTag("starIcon",true)
            .onFirst()
            .performClick()

        Assert.assertTrue(navController.currentDestination?.route == FavoritesDestination.route)

        composeTestRule
            .waitUntilExactlyOneExists(hasTestTag("favoriteCard"))

        composeTestRule
            .onAllNodesWithTag("favoriteCard")
            .onFirst()
            .performClick()
        /*
        composeTestRule
            .onNodeWithTag("teamOverviewHeader")
            .assertExists()

        composeTestRule
            .onAllNodesWithTag("bottomAppBarLabel",true)
            .get(1)
            .performClick()
        composeTestRule
            .onAllNodesWithTag("playerCard")
            .fetchSemanticsNodes(true)*/
    }

    @Test
    fun canNavigateBackFromTeamsScreen() {
        composeTestRule
            .onNodeWithText("Choose a league")
        composeTestRule
            .onAllNodesWithTag("leagueCard")
            .onFirst()
            .performClick()
        composeTestRule
            .onNodeWithTag("backArrow",true)
            .performClick()
        composeTestRule
            .onAllNodesWithText("Choose a league")
    }
}