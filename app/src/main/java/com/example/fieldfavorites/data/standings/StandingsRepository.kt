package com.example.fieldfavorites.data.standings

import com.example.fieldfavorites.model.Standings

interface StandingsRepository {
    suspend fun getStandingsForLeague(leagueId: Int): List<Standings>
}