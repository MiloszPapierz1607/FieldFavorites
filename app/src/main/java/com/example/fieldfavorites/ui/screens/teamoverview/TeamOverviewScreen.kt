package com.example.fieldfavorites.ui.screens.teamoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.fieldfavorites.ActionMenuItem
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.FixtureTeam
import com.example.fieldfavorites.model.Standings
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.components.LoadingComponent
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object TeamOverviewDestination : NavigationDestination {
    override val route = "teamoverview"
    const val itemIdArg = "teamId"
    const val itemNameArg = "teamName"
    val routeWithArgs = "${route}/{$itemIdArg}/{$itemNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamOverviewScreen(
    goBack: () -> Unit,
    removeFromFavorite: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    teamOverviewViewModel: TeamOverviewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val teamOverviewUiState by teamOverviewViewModel.uiState.collectAsState()
    val teamOverviewApiState = teamOverviewViewModel.teamOverviewApiState

    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = teamOverviewViewModel.teamName,
                canNavigateBack = true,
                navigateUp = goBack,
                showActionsMenu = true,
                items = listOf(
                    ActionMenuItem.NeverShown(
                        title = "Remove from favorites",
                        onClick = {removeFromFavorite(teamOverviewViewModel.teamId)}
                    )
                )
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            when(teamOverviewApiState) {
                is TeamOverviewApiState.Loading -> LoadingComponent()
                is TeamOverviewApiState.Error -> Text("Something went wrong. Try again later!")
                is TeamOverviewApiState.Success -> OverviewScreen(teamOverviewUiState.nextFixture!!,teamOverviewUiState.standings)

            }

        }
    }
}

@Composable
fun OverviewScreen(nextFixture: FixtureRow, standings: List<Standings>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(8.dp,12.dp)
    ) {
        Text(
            text="Next game",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        NextFixtureCard(nextFixture= nextFixture)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Standings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Standings(standings)
    }
}

@Composable
fun Standings(standings: List<Standings>,modifier: Modifier = Modifier) {
    Row {
        Text("",modifier = Modifier.weight(2f))
        Text("G",modifier = Modifier.weight(0.5f))
        Text("W",modifier = Modifier.weight(0.5f))
        Text("D",modifier = Modifier.weight(0.5f))
        Text("L",modifier = Modifier.weight(0.5f))
        Text("P",modifier = Modifier.weight(0.5f))

    }
    Column {
        standings.forEach {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
            ) {
                Text("${it.rank}. ${it.team.name}",modifier = Modifier.weight(2f))
                Text(it.all.played.toString(),modifier = Modifier.weight(0.5f))
                Text(it.all.win.toString(),modifier = Modifier.weight(0.5f))
                Text(it.all.draw.toString(),modifier = Modifier.weight(0.5f))
                Text(it.all.lose.toString(),modifier = Modifier.weight(0.5f))
                Text(it.points.toString(),modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@Composable
fun NextFixtureCard(nextFixture: FixtureRow,modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        val indexOfHyphen = nextFixture.league.round.indexOf('-')
        val matchday = nextFixture.league.round.substring(indexOfHyphen+1)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(nextFixture.fixture.venue.name)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Matchday $matchday")
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NextFixtureCardTeam(
                    team = nextFixture.teams.home,
                    modifier = Modifier.weight(1f)
                )
                Text("VS")
                NextFixtureCardTeam(
                    team = nextFixture.teams.away,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun NextFixtureCardTeam(team: FixtureTeam,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(team.logo)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription ="${team.name} logo",
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(team.name)
    }
}