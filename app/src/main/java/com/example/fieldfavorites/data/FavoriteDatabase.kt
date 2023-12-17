package com.example.fieldfavorites.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fieldfavorites.data.favorites.FavoriteDao
import com.example.fieldfavorites.model.Team

/**
 * Database class with a singleton Instance object.
 * */
@Database(entities = [Team::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}