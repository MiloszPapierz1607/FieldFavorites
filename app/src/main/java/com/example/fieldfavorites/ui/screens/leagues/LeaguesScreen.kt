package com.example.fieldfavorites.ui.screens.leagues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.ui.AppViewModelProvider

@Composable
fun LeaguesScreen(modifier: Modifier = Modifier,leagueViewModel: LeagueViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val leagueUiState by leagueViewModel.uiState.collectAsState()

    Column {
        val lazyListState = rememberLazyListState()
        LazyColumn(state = lazyListState) {
            items(leagueUiState.leagues) {
                LeagueCard(
                    leagueName = it.name,
                    flagSvgUrl = it.countryFlagSvg,
                    logoUrl = it.logoImageUrl,
                    modifier = modifier.padding(12.dp,24.dp)
                )
            }
        }
    }
}