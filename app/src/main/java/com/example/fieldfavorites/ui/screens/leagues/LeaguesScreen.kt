package com.example.fieldfavorites.ui.screens.leagues

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object LeaguesDestination : NavigationDestination {
    override val route = "leagues"
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LeaguesScreen(
    navigateToTeams: (Int) -> Unit,
    modifier: Modifier = Modifier,
    leagueViewModel: LeagueViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val leagueUiState by leagueViewModel.uiState.collectAsState()

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar()
        }
    ) {
        innerPadding ->
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            ),
            exit = fadeOut(),
            modifier = modifier
                .padding(innerPadding),
        ) {
            Column {
                leagueUiState.leagues.forEachIndexed {index,it ->
                    LeagueCard(
                        leagueName = it.name,
                        flagSvgUrl = it.countryFlagSvg,
                        logoUrl = it.logoImageUrl,
                        modifier = modifier
                            .padding(12.dp, 24.dp)
                            .clickable {
                                navigateToTeams(it.id)
                            }
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = Spring.StiffnessVeryLow,
                                        dampingRatio = Spring.DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) }
                                )
                            )
                    )
                }
            }
        }
    }
}