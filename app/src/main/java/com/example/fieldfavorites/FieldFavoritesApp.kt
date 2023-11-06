package com.example.fieldfavorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fieldfavorites.ui.navigation.FieldFavoritesNavHost

@Composable
fun FieldFavoritesApp(navController: NavHostController = rememberNavController()) {
    FieldFavoritesNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFavoritesTopAppBar(
    title:String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {Text(title)},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}