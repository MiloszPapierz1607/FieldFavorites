package com.example.fieldfavorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fieldfavorites.ui.navigation.FieldFavoritesNavHost

/**
 * Top level composable that represents screens for the application.
 * */
@Composable
fun FieldFavoritesApp(navController: NavHostController = rememberNavController()) {
    FieldFavoritesNavHost(navController = navController)
}

/**
 * App bar to display bottom app bar items.
 * @param bottomAppBarItems represents the items that will be displayed in the bottom bar
* */
@Composable
fun FieldFavoritesBottomAppBar(
    bottomAppBarItems: List<BottomAppBarItem>
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        bottomAppBarItems.forEach {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = it.onClick) {
                    Icon(
                        it.icon,
                        "",
                        modifier=Modifier.testTag("bottomAppBarLabel")
                    )
                }
                if(it is BottomAppBarItem.BottomAppBarItemWithLabel) {
                    Text(it.label)
                }
            }
        }
    }
}


/**
 * App bar to display title and conditionally display the back navigation.
 * It also receives a [List] of [ActionMenuItem] that will be displayed in a dropdown menu if [showActionsMenu] is true.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFavoritesTopAppBar(
    title:String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    showActionsMenu: Boolean = false,
    items: List<ActionMenuItem> = listOf()
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    CenterAlignedTopAppBar(
        title = {Text(title,modifier = Modifier.testTag("screenTitle"))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.testTag("backArrow")
                    )
                }
            }
        },
        actions = {
            if (showActionsMenu) {
                IconButton(onClick = { menuExpanded = !menuExpanded}) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null
                    )
                }


                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    items.forEach {
                        DropdownMenuItem(
                            text = {
                                   Text(it.title)
                            },
                            onClick = {
                                it.onClick()
                            }
                        )
                    }
                }
            }
        }
    )
}

/**
 * Interface that represents objects that will be displayed in the dropdown menu in the top app bar.
 * */
sealed interface ActionMenuItem {
    val title: String
    val onClick: () -> Unit

    data class NeverShown(
        override  val title: String,
        override val onClick: () -> Unit
    ) : ActionMenuItem
}

/**
 * Interface that represents objects that will be displayed in the bottom app bar.
 * */
sealed interface BottomAppBarItem {
    val icon: ImageVector
    val onClick: () -> Unit

    data class BottomAppBarItemDefault(
        override val icon: ImageVector,
        override val onClick: () -> Unit
    ) : BottomAppBarItem

    data class BottomAppBarItemWithLabel(
        override val icon: ImageVector,
        override val onClick: () -> Unit,
        val label: String
    ) : BottomAppBarItem
}