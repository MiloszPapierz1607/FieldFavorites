package com.example.fieldfavorites.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val logo: String
)
