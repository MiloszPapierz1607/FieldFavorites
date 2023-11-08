package com.example.fieldfavorites.ui.screens.favorites

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.navigation.NavigationDestination

object FavoritesDestination : NavigationDestination {
    override val route = "favorites"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
    ) {
    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = "Your favorite clubs",
                canNavigateBack = false
            )
        }
    ) {
        Text("Test", modifier = modifier.padding(it))
    }
}