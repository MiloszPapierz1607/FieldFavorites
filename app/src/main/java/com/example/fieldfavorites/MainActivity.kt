package com.example.fieldfavorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.ui.component.LeagueCard
import com.example.fieldfavorites.ui.theme.FieldFavoritesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FieldFavoritesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FieldFavoritesApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFavoritesApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title =  {
                    Text("Choose a league")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LeagueCard(
                modifier = Modifier.
                padding(horizontal = 16.dp, vertical = 32.dp)
            )
            LeagueCard(
                modifier = Modifier.
                padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

    }
}