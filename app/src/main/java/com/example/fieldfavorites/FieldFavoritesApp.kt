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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fieldfavorites.ui.navigation.FieldFavoritesNavHost
import com.example.fieldfavorites.ui.screens.leagues.LeaguesScreen

@Composable
fun FieldFavoritesApp(navController: NavHostController = rememberNavController()) {
    FieldFavoritesNavHost(navController = navController)
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