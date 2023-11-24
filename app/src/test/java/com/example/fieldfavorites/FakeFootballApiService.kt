package com.example.fieldfavorites.fake

import com.example.fieldfavorites.model.ApiFixtureResponse
import com.example.fieldfavorites.model.ApiResponse
import com.example.fieldfavorites.model.PlayerApiResponse
import com.example.fieldfavorites.model.StandingApiResponse
import com.example.fieldfavorites.network.FootballApiService

class FakeFootballApiService: FootballApiService {
    override suspend fun getTeamsByLeagueId(leagueId: Int, season: Int): ApiResponse {
        return FakeNetworkDataSource.apiResponse
    }

    override suspend fun getFixturesByTeamId(
        teamId: Int,
        nextGamesCount: Int,
        season: Int
    ): ApiFixtureResponse {
        return FakeNetworkDataSource.apiFixtureResonse
    }

    override suspend fun getStandingsForLeague(leagueId: Int, season: Int): StandingApiResponse {
        return FakeNetworkDataSource.standingApiResponse
    }

    override suspend fun getPlayersByTeamId(
        teamId: Int,
        page: Int,
        season: Int
    ): PlayerApiResponse {
        return FakeNetworkDataSource.playerApiResponse
    }
}