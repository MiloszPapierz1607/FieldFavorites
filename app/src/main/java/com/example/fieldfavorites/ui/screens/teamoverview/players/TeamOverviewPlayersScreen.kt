package com.example.fieldfavorites.ui.screens.teamoverview.players

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fieldfavorites.DeviceType
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.ui.components.ReusableCard

@Composable
fun TeamOverviewPlayersScreen(deviceType: DeviceType,playerStats: List<PlayerRow>,modififer: Modifier = Modifier) {
    if(deviceType == DeviceType.MOBILE) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            item {
                Text(
                    text = "Players",
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
                        .testTag("playerCard")
                )
            }
        }
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2),modifier = Modifier
            .padding(horizontal = 12.dp)) {
            item(span = {
                GridItemSpan(2)
            }){
                Text(
                    text = "Players",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(playerStats) {
                PlayerCard(
                    playerData = it,
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 12.dp)
                        .testTag("playerCard")
                )
            }
        }
    }

}

@Composable
fun PlayerCard(playerData: PlayerRow,modifier: Modifier = Modifier) {
    val nmbrOfGoals = playerData.statistics.filter { it.goals.total != null}.map { it.goals.total!! }.sum()

    ReusableCard(
        modifier = modifier
    ) {
        Row(
            modifier = it
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