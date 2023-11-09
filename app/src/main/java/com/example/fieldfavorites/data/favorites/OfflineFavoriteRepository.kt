package com.example.fieldfavorites.data.favorites

import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override fun getAllFavoriteTeamsStream(): Flow<List<Team>> = favoriteDao.getAllFavorites()

    override suspend fun insertFavoriteTeam(team: Team) = favoriteDao.insert(team)

    override suspend fun deleteTeam(team: Team) = favoriteDao.delete(team)
}