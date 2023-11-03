package com.example.fieldfavorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fieldfavorites.ui.screens.leagues.LeaguesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFavoritesApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FieldFavoritesTopAppBar()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LeaguesScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFavoritesTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
        Text(stringResource(R.string.leagues_screen_title))
    }
    )
}