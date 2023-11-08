package com.example.fieldfavorites.network

import com.example.fieldfavorites.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApiService {
    @GET("teams")
    suspend fun getLeagues(@Query("league") leagueId: Int,@Query("season") season: Int = 2023): ApiResponse
}