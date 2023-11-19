package com.example.fieldfavorites.model

import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    val current: Int,
    val total: Int
)
