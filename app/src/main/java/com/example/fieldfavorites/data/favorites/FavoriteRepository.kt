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
     * Retrieve a team by given [teamId]
    * */
    suspend fun getTeamById(teamId: Int) : Team?

    /**
     * Insert a given [team] in the data source
     */
    suspend fun insertFavoriteTeam(team:Team)

    /**
     * Delete a [Team]  by given [teamId] from the data source
     */
    suspend fun deleteTeam(teamId: Int)
}