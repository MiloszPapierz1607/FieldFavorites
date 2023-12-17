package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class StandingApiResponse(
    val response: Array<Standing>
)

@Serializable
data class StandingTeam(
    val id: Int,
    val name: String,
    val logo: String
)

@Serializable
data class StandingAll(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int
)

@Serializable
data class Standings(
    val rank: Int,
    val points: Int,
    val goalsDiff: Int,
    val team: StandingTeam,
    val all: StandingAll
)

@Serializable
data class StandingLeague(
    val id: Int,
    val name: String,
    val standings: Array<Array<Standings>>
)

@Serializable
data class Standing(
    val league: StandingLeague
)
