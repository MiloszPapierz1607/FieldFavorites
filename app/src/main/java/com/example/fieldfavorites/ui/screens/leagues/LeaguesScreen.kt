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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.navigation.NavigationDestination
import com.example.fieldfavorites.R

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
            FieldFavoritesTopAppBar(
                title = stringResource(R.string.leagues_screen_title),
                canNavigateBack = false
            )
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

@Composable
fun LeagueCard(
    leagueName: String,
    logoUrl: String,
    flagSvgUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(logoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "$leagueName logo image",
                    contentScale = ContentScale.Crop,
                )
            }
            Text(
                text = leagueName,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(52.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(flagSvgUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription ="Logo",
                )
            }
        }
    }
}