package com.example.fieldfavorites.data.leagues

import com.example.fieldfavorites.model.League

interface LeagueRepository {
    fun getTop5Leagues(): List<League>
}