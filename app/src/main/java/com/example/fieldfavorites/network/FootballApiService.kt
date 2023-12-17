package com.example.fieldfavorites.network

import com.example.fieldfavorites.model.ApiFixtureResponse
import com.example.fieldfavorites.model.ApiResponse
import com.example.fieldfavorites.model.PlayerApiResponse
import com.example.fieldfavorites.model.StandingApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A variable that represents the year of current season.
 * This variable is used by all functions from [FootballApiService] to retrieve data from the current season.
 * Example: 2022 would use data from season 2022-2023.
 * 2023 would use data from season 2023-2024 and so on.
 * */
const val currentSeasonYear = 2023

/**
 * A public interface that exposes the [getLeagues], [getFixturesByTeamId], [getStandingsForLeague] and [getPlayersByTeamId]
 * methods.
 * */
interface FootballApiService {
    /**
     * Returns a [ApiResponse] object. This function can be called from a Coroutine.
     * The @GET annotation indicates that the /teams endpoint will be used.
     * @param leagueId Id of the league to fetch the teams from.
     * @param season Specifies the seasons the data needs to be fetched from. [currentSeasonYear] is used by default.
     * */
    @GET("teams")
    suspend fun getTeamsByLeagueId(
        @Query("league") leagueId: Int,
        @Query("season") season: Int = currentSeasonYear
    ): ApiResponse

    /**
     * Returns a [ApiFixtureResponse] object. This function can be called from a Coroutine.
     * The @GET annotation indicates that the /fixtures endpoint will be used.
     * @param teamId Id of the team the fixtures will be fetched from.
     * @param nextGamesCount Number of the upcoming fixtures that needs to be fetched.
     * @param season Specifies the seasons the data needs to be fetched from. [currentSeasonYear] is used by default.
     * */
    @GET("fixtures")
    suspend fun getFixturesByTeamId(
        @Query("team") teamId: Int,
        @Query("next") nextGamesCount: Int,
        @Query("season") season: Int = currentSeasonYear
    ): ApiFixtureResponse

    /**
     * Returns a [StandingApiResponse] object. This function can be called from a Coroutine.
     * The @GET annotation indicates that the /standings endpoint will be used.
     * @param leagueId Id of the league the current standing will be fetched from.
     * @param season Specifies the seasons the data needs to be fetched from. [currentSeasonYear] is used by default.
     * */
    @GET("standings")
    suspend fun getStandingsForLeague(
        @Query("league") leagueId: Int,
        @Query("season") season: Int = currentSeasonYear
    ): StandingApiResponse

    /**
     * Returns a [PlayerApiResponse] object. This function can be called from a Coroutine.
     * The @GET annotation indicates that the /players endpoint will be used.
     * @param teamId Id of the team the players will be fetched from.
     * @param page Number of the page the players will be fetched from. By default 1 is used.
     * @param season Specifies the seasons the data needs to be fetched from. [currentSeasonYear] is used by default.
     * */
    @GET("players")
    suspend fun getPlayersByTeamId(
        @Query("team") teamId: Int,
        @Query("page") page: Int = 1,
        @Query("season") season: Int = currentSeasonYear
    ): PlayerApiResponse
}