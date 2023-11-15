package com.example.fieldfavorites.data.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Football database
 */
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM teams")
    fun getAllFavorites(): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(team: Team)

    @Query("DELETE FROM teams WHERE id = :teamId")
    suspend fun delete(teamId: Int)
}