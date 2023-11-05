package com.example.fieldfavorites

import android.app.Application
import com.example.fieldfavorites.data.AppContainer
import com.example.fieldfavorites.data.AppDataContainer

class FieldFavoritesApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     * */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}