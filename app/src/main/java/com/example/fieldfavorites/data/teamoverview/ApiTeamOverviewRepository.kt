package com.example.fieldfavorites.data.teamoverview

import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.network.FootballApiService

class ApiTeamOverviewRepository(
    private val footballApiService: FootballApiService
) : TeamOverviewRepository {
    override suspend fun getFixturesByTeamId(teamId: Int, nextGamesCount: Int): List<FixtureRow> {
        val response = footballApiService.getFixturesByTeamId(teamId, nextGamesCount)
        return response.response.toList()
    }

    override suspend fun getPlayersByTeamId(teamId: Int): List<PlayerRow> {
        var pageNumber = 1
        val initialResponse =
            footballApiService.getPlayersByTeamId(teamId = teamId, page = pageNumber)
        var playersData = initialResponse.response.toList()
        pageNumber++

        while (pageNumber <= initialResponse.paging.total) {
            val response = footballApiService.getPlayersByTeamId(teamId = teamId, page = pageNumber)
            playersData = playersData.plus(response.response.toList())
            pageNumber++
        }

        return playersData
    }
}