package com.example.fieldfavorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.fieldfavorites.ui.theme.FieldFavoritesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FieldFavoritesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var deviceType:DeviceType
                    val windowSize = calculateWindowSizeClass(activity = this)
                    if(windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                        deviceType = DeviceType.MOBILE
                    } else {
                        deviceType = DeviceType.TABLET
                    }

                    FieldFavoritesApp(deviceType)
                }
            }
        }
    }
}