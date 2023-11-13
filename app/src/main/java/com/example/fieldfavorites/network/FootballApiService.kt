package com.example.fieldfavorites.network

import com.example.fieldfavorites.model.ApiFixtureResponse
import com.example.fieldfavorites.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val currentSeasonYear = 2023

interface FootballApiService {
    @GET("teams")
    suspend fun getLeagues(@Query("league") leagueId: Int,@Query("season") season: Int = currentSeasonYear): ApiResponse

    @GET("fixtures")
    suspend fun getFixturesByTeamId(@Query("team")teamId: Int,@Query("next") nextGamesCount: Int,@Query("season") season: Int = currentSeasonYear) : ApiFixtureResponse
}