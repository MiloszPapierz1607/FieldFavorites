package com.example.fieldfavorites.data.standings

import com.example.fieldfavorites.model.Standings
import com.example.fieldfavorites.network.FootballApiService

class StandingsApiRepository(
    private val footballApiService: FootballApiService
) : StandingsRepository {
    override suspend fun getStandingsForLeague(leagueId: Int): List<Standings> {
        val response = footballApiService.getStandingsForLeague(leagueId)
        return response.response[0].league.standings[0].toList()
    }
}