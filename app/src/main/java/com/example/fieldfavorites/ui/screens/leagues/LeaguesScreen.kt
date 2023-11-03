package com.example.fieldfavorites.ui.screens.leagues

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.ui.AppViewModelProvider

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LeaguesScreen(modifier: Modifier = Modifier,leagueViewModel: LeagueViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val leagueUiState by leagueViewModel.uiState.collectAsState()

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Column {
            leagueUiState.leagues.forEachIndexed {index,it ->
                LeagueCard(
                    leagueName = it.name,
                    flagSvgUrl = it.countryFlagSvg,
                    logoUrl = it.logoImageUrl,
                    modifier = modifier
                        .padding(12.dp,24.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = {it * (index +1)}
                            )
                        )
                )
            }
        }
    }
}