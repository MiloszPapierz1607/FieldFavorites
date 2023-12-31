package com.example.fieldfavorites.ui.screens.favorites

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fieldfavorites.DeviceType
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.R
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.ui.components.LoadingComponent
import com.example.fieldfavorites.ui.components.ReusableCard
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object FavoritesDestination : NavigationDestination {
    override val route = "favorites"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FavoritesScreen(
    favoriteTeamApiState: FavoriteTeamApiState,
    deviceType: DeviceType,
    favoriteTeams: List<Team>,
    navigateToOverviewScreen: (Int, String) -> Unit,
    navigateToLeagueScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = stringResource(R.string.favorites_screen_title),
                canNavigateBack = false,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToLeagueScreen() },
                modifier = Modifier.testTag("addButton")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.favorites_add_button_description)
                )
            }
        }
    ) {
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            ),
            exit = fadeOut(),
            modifier = modifier
                .padding(it),
        ) {
            when (favoriteTeamApiState) {
                is FavoriteTeamApiState.Loading -> LoadingComponent()
                is FavoriteTeamApiState.Error -> Text("Something went wrong. Try again later!")
                is FavoriteTeamApiState.Success -> if (deviceType == DeviceType.MOBILE) {
                    LazyColumn {
                        itemsIndexed(favoriteTeams) { index, it ->
                            FavoriteTeamCard(
                                team = it,
                                modifier = modifier
                                    .padding(12.dp, 24.dp)
                                    .testTag("favoriteCard")
                                    .clickable {
                                        navigateToOverviewScreen(it.id, it.name)
                                    }
                                    .animateEnterExit(
                                        enter = slideInVertically(
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessVeryLow,
                                                dampingRatio = Spring.DampingRatioLowBouncy
                                            ),
                                            initialOffsetY = { it * (index + 1) },
                                        )
                                    )

                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        itemsIndexed(favoriteTeams) { index, it ->
                            FavoriteTeamCard(
                                team = it,
                                modifier = modifier
                                    .padding(12.dp, 24.dp)
                                    .testTag("favoriteCard")
                                    .clickable {
                                        navigateToOverviewScreen(it.id, it.name)
                                    }
                                    .animateEnterExit(
                                        enter = slideInVertically(
                                            animationSpec = spring(
                                                stiffness = Spring.StiffnessVeryLow,
                                                dampingRatio = Spring.DampingRatioLowBouncy
                                            ),
                                            initialOffsetY = { it * (index + 1) },
                                        )
                                    )

                            )
                        }
                    }
                }
            }

        }
    }

}

@Composable
fun FavoriteTeamCard(
    team: Team,
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
                    .sizeIn(52.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.favorites_club_image, team.name),
                    contentScale = ContentScale.Crop,
                )
            }
            Text(
                text = team.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.favorites_club_card_arrow_icon)
                )
            }
        }
    }
}