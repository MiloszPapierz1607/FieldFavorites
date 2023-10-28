package com.example.fieldfavorites.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldfavorites.R
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.fieldfavorites.ui.theme.FieldFavoritesTheme

@Composable
fun LeagueCard(modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.premier),
                    contentDescription = "fdas"
                )
            }
            Text(
                text = "Premier League",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(52.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://media-4.api-sports.io/flags/gb.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription ="Logo",
                )
            }
        }
    }
}


@Preview()
@Composable
private fun LeagueCardPreview() {
    FieldFavoritesTheme {
        Column{
            LeagueCard(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 16.dp)
            )
            LeagueCard(modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
            )
        }
    }
}