package com.example.fieldfavorites.ui.screens.teamoverview.players

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fieldfavorites.model.PlayerRow

@Composable
fun TeamOverviewPlayersScreen(playerStats: List<PlayerRow>,modififer: Modifier = Modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            item {
                Text(
                    text="Players",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(playerStats) {
                PlayerCard(
                    playerData = it,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                )
            }
        }

}

@Composable
fun PlayerCard(playerData: PlayerRow,modifier: Modifier = Modifier) {
    val nmbrOfGoals = playerData.statistics.filter { it.goals.total != null}.map { it.goals.total!! }.sum()

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 52.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(playerData.player.photo)
                    .crossfade(true)
                    .build(),
                contentDescription = "${playerData.player.name} image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                Text(playerData.player.name)
                Text("$nmbrOfGoals Goal(s)")
            }
        }
    }
}