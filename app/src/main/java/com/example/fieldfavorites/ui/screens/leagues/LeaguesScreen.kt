package com.example.fieldfavorites.ui.screens.leagues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LeaguesScreen(modifier: Modifier = Modifier,leagueViewModel: LeagueViewModel = viewModel()) {
    val test = leagueViewModel.leagueUiState

    Column {
        LeagueCard(
            modifier = modifier.padding(4.dp,16.dp)
        )
        LeagueCard(
            modifier = modifier.padding(4.dp,16.dp)
        )
    }
}