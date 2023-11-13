package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiFixtureResponse(
    val response: Array<FixtureRow>
)

@Serializable
data class FixtureLeague(
    val round: String
)

@Serializable
data class FixtureTeam(
    val id: Int,
    val name: String,
    val logo: String,
)

@Serializable
data class FixtureTeams(
    val home: FixtureTeam,
    val away: FixtureTeam
)

@Serializable
data class FixtureRow(
    val fixture: Fixture,
    val league: FixtureLeague,
    val teams: FixtureTeams
)

@Serializable
data class FixtureVenue(
    val name: String
)

@Serializable
data class Fixture(
    val venue: FixtureVenue
)
