package com.example.fieldfavorites.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A component that uses [Card] composable to define a default styling to keep consistency across the whole app.
 * */
@Composable
fun ReusableCard(
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        content(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 52.dp))
    }
}