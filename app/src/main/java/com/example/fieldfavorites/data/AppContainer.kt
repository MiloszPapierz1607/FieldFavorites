package com.example.fieldfavorites.data

import android.content.Context
import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.data.leagues.NoApiLeagueRepository

interface AppContainer {
    val leagueRepository: LeagueRepository
}

/**
* [AppContainer] implementation that provides instance of [NoApiLeagueRepository]
* */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [LeagueRepository]
     * */
    override val leagueRepository: LeagueRepository by lazy {
        NoApiLeagueRepository()
    }
}