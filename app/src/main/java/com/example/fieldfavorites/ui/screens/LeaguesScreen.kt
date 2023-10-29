package com.example.fieldfavorites.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard

@Composable
fun LeaguesScreen(modifier: Modifier = Modifier) {
    Column {
        LeagueCard(
            modifier = modifier.padding(4.dp,16.dp)
        )
        LeagueCard(
            modifier = modifier.padding(4.dp,16.dp)
        )
    }
}