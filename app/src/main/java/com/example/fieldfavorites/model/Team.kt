package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val response: Array<ResponseObject>
)

@Serializable
data class ResponseObject(
    val team: Team,
)

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val logo: String
)
