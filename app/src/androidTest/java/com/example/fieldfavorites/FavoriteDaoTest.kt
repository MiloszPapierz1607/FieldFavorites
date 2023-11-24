package com.example.fieldfavorites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fieldfavorites.data.FavoriteDatabase
import com.example.fieldfavorites.data.favorites.FavoriteDao
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var favoriteDatabase: FavoriteDatabase
    private val team1 = Team(1,"Real Madrid","logo.url",4)
    private val team2 = Team(2,"Fc Barcelona","logo.url",4)

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        favoriteDatabase = Room.inMemoryDatabaseBuilder(context,FavoriteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = favoriteDatabase.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        favoriteDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsTeamIntoDB() = runBlocking {
        addTeamToDb()
        val allItems = favoriteDao.getAllFavorites().first()
        Assert.assertEquals(allItems[0],team1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDb() = runBlocking {
        addTwoTeamToDb()
        val allItems = favoriteDao.getAllFavorites().first()
        Assert.assertEquals(allItems[0],team1)
        Assert.assertEquals(allItems[1],team2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetTeamById_returnsTeamFromDb() = runBlocking {
        addTwoTeamToDb()
        val team = favoriteDao.getTeamById(1)
        Assert.assertEquals(team,team1)
    }

    @Test
    @Throws(Exception::class)
    fun datDeleteTeams_deletesAllItemsFromDB() = runBlocking {
        addTwoTeamToDb()
        favoriteDao.delete(1)
        favoriteDao.delete(2)
        val allItems = favoriteDao.getAllFavorites().first()
        Assert.assertTrue(allItems.isEmpty())

    }

    private suspend fun addTeamToDb() {
        favoriteDao.insert(team1)
    }

    private suspend fun addTwoTeamToDb() {
        favoriteDao.insert(team1)
        favoriteDao.insert(team2)
    }
}