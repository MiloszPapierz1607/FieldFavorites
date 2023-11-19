package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerApiResponse(
    val paging: Paging,
    val response: Array<PlayerRow>
)

@Serializable
data class PlayerRow(
    val player: Player,
    val statistics: Array<StatisticRow>
)

@Serializable
data class StatisticGame(
    val appearences: Int?,
    val minutes: Int?,
    val position: String
)

@Serializable
data class StatisitcGoals(
    val total: Int?
)

@Serializable
data class StatisticRow(
    val games: StatisticGame,
    val goals: StatisitcGoals

)

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val firstname: String?,
    val lastname: String?,
    val photo: String
)
