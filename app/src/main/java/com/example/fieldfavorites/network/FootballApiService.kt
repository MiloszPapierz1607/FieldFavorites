package com.example.fieldfavorites.network

import com.example.fieldfavorites.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://v3.football.api-sports.io/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json {ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(OkHttpClient
        .Builder().
        addInterceptor {
         it.
         proceed(it.request()
             .newBuilder()
             .addHeader("x-rapidapi-key",BuildConfig.API_FOOTBALL_KEY)
             .addHeader("x-rapidapi-host","v3.football.api-sports.io")
             .build())
    }.build())
    .build()