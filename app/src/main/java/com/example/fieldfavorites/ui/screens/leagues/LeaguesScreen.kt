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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.fieldfavorites.DeviceType
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.R
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.components.ReusableCard
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object LeaguesDestination : NavigationDestination {
    override val route = "leagues"
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LeaguesScreen(
    deviceType: DeviceType,
    navigateToFavorites: () -> Unit,
    canNavigateBack: Boolean = false,
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
                canNavigateBack = canNavigateBack,
                navigateUp = navigateToFavorites
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
            if(deviceType == DeviceType.MOBILE) {
            Column {
                    leagueUiState.leagues.forEachIndexed {index,it ->
                        LeagueCard(
                            leagueName = it.name,
                            flagSvgUrl = it.countryFlagSvg,
                            logoUrl = it.logoImageUrl,
                            modifier = modifier
                                .padding(12.dp, 24.dp)
                                .testTag("leagueCard")
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
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    itemsIndexed(leagueUiState.leagues) {index,it ->
                        LeagueCard(
                            leagueName = it.name,
                            logoUrl = it.logoImageUrl,
                            flagSvgUrl = it.countryFlagSvg,
                            modifier = modifier
                                .padding(12.dp, 24.dp)
                                .testTag("leagueCard")
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
}

@Composable
fun LeagueCard(
    leagueName: String,
    logoUrl: String,
    flagSvgUrl: String,
    modifier: Modifier = Modifier
) {
    ReusableCard(
        modifier = modifier
    ) {
        Row(
            modifier = it,
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
                    contentDescription = stringResource(R.string.leagues_league_logo,leagueName),
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
                    contentDescription = stringResource(R.string.leagues_flag_image),
                )
            }
        }
    }
}