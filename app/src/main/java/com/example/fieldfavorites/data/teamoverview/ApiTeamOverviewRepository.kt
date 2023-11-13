package com.example.fieldfavorites.data.teamoverview

import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.network.FootballApiService

class ApiTeamOverviewRepository(
    private val footballApiService: FootballApiService
) : TeamOverviewRepository {
    override suspend fun getFixturesByTeamId(teamId: Int, nextGamesCount: Int): List<FixtureRow> {
        val response = footballApiService.getFixturesByTeamId(teamId,nextGamesCount)
        return response.response.toList()
    }
}