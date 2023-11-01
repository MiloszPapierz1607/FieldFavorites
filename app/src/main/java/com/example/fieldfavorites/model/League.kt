package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val response: List<LeagueResponse>
)

@Serializable
data class LeagueResponse(
    val league: League,
    val country: Country
)

@Serializable
data class League(
    val id: Int,
    val name: String,
    val logo: String
)

@Serializable
data class Country(
    val flag: String?
)
