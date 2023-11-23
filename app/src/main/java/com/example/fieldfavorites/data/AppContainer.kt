package com.example.fieldfavorites.data

import android.content.Context
import com.example.fieldfavorites.BuildConfig
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.favorites.OfflineFavoriteRepository
import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.data.leagues.NoApiLeagueRepository
import com.example.fieldfavorites.data.standings.StandingsApiRepository
import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.data.teamoverview.ApiTeamOverviewRepository
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.data.teams.ApiTeamRepository
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.network.FootballApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * App container for dependency injection.
 * */
interface AppContainer {
    val leagueRepository: LeagueRepository
    val teamRepository: TeamRepository
    val favoriteRepository: FavoriteRepository
    val teamOverviewRepository: TeamOverviewRepository
    val standingsRepository: StandingsRepository
}

/**
* [AppContainer] implementation that provides dependency injection container at the application level
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
* */
class AppDataContainer(private val context: Context) : AppContainer {
    private val BASE_URL = "https://v3.football.api-sports.io/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json {ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient
            .Builder().
            addInterceptor {
                it.
                proceed(it.request()
                    .newBuilder()
                    .addHeader("x-rapidapi-key", BuildConfig.API_FOOTBALL_KEY)
                    .addHeader("x-rapidapi-host","v3.football.api-sports.io")
                    .build())
            }.build())
        .build()

    /**
     * Retrofit service object for creating api calls to football-api
     */
    private val retrofitService: FootballApiService by lazy {
        retrofit.create(FootballApiService::class.java)
    }

    /**
     * Implementation for [LeagueRepository] that returns hard coded leagues to save api calls
     * */
    override val leagueRepository: LeagueRepository by lazy {
        NoApiLeagueRepository()
    }

    /**
     * Implementation for [TeamRepository] that fetches teams using [FootballApiService]
     */
    override val teamRepository: TeamRepository by lazy {
        ApiTeamRepository(retrofitService)
    }

    /**
     * Implementation for [FavoriteRepository] that returns favorite teams from a user using [FavoriteDatabase]
     */
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FavoriteDatabase.getDatabase(context).favoriteDao())
    }

    /**
     * Implementation for [TeamOverviewRepository] that fetches data such as next fixture and players using [FootballApiService]
     */
    override val teamOverviewRepository: ApiTeamOverviewRepository by lazy {
        ApiTeamOverviewRepository(retrofitService)
    }

    /**
    * Implementation for [StandingsRepository] that fetches the current standing using [FootballApiService]
    * */
    override val standingsRepository: StandingsApiRepository by lazy {
        StandingsApiRepository(retrofitService)
    }
}