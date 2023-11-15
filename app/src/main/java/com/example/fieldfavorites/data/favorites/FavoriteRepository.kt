package com.example.fieldfavorites.data.favorites

import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, delete and retrieve of [Team] from a given data source
 */
interface FavoriteRepository {
    /**
     * Retrieve all the teams from the given data source
     */
    fun getAllFavoriteTeamsStream() : Flow<List<Team>>

    /**
     * Insert team in the data source
     */
    suspend fun insertFavoriteTeam(team:Team)

    /**
     * Delete team from the data source
     */
    suspend fun deleteTeam(teamId: Int)
}