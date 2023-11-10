package com.example.fieldfavorites.ui.screens.teams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.R
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamsDestination : NavigationDestination {
    override val route = "teams"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(favoriteTeamsIds: List<Int>,navigateBack: () -> Unit,modifier: Modifier = Modifier,teamViewModel: TeamViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val teamUiState by teamViewModel.uiState.collectAsState()
    val teams = teamUiState.teams

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = stringResource(R.string.teams_screen_title),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
            LazyColumn(
                modifier = modifier.padding(it)
            ) {
                items(teams) {
                    TeamCard(
                        addToFavorite = {
                            teamViewModel.insertFavoriteTeam(it)
                        },
                        team = it,
                        isAdded = favoriteTeamsIds.contains(it.id),
                        modifier = modifier
                            .padding(12.dp, 24.dp)
                    )
                }
            }
        }
}

@Composable
fun TeamCard(
    isAdded: Boolean = false,
    addToFavorite:() -> Unit,
    team:Team,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
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
                    .sizeIn(52.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${team.name} logo image",
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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .border(
                        border = BorderStroke(1.dp, SolidColor(MaterialTheme.colorScheme.outline)),
                        shape = CircleShape
                    )
                   ,
                contentAlignment = Alignment.Center,

            ) {
                IconButton(onClick = {
                    if(!isAdded) {
                        addToFavorite()
                    }
                }) {
                    Icon(
                        imageVector = if(isAdded) Icons.Filled.Star else Icons.TwoTone.Star,
                        contentDescription = null,
                    )
                }
            }

        }
    }
}