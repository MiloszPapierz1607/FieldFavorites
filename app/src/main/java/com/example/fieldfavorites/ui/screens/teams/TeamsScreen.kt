package com.example.fieldfavorites.ui.screens.teams

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldfavorites.FieldFavoritesTopAppBar
import com.example.fieldfavorites.ui.AppViewModelProvider
import com.example.fieldfavorites.ui.navigation.NavigationDestination
import com.example.fieldfavorites.R;

object TeamsDestination : NavigationDestination {
    override val route = "teams"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(modifier: Modifier = Modifier,navigateBack: () -> Unit,teamViewModel: TeamViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    Scaffold(
        topBar = {
            FieldFavoritesTopAppBar(
                title = stringResource(R.string.teams_screen_title),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {it ->
        Text(modifier = modifier.padding(it), text = "Test")
    }
}