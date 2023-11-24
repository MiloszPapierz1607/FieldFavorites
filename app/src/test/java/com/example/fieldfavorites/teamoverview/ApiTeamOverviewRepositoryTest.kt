package com.example.fieldfavorites.teamoverview

import com.example.fieldfavorites.data.teamoverview.ApiTeamOverviewRepository
import com.example.fieldfavorites.fake.FakeFootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ApiTeamOverviewRepositoryTest {
    @Test
    fun apiTeamOverviewRepository_getFixturesByTeamId_verifyFixturesList() =
        runTest {
            val repository = ApiTeamOverviewRepository(FakeFootballApiService())
            val response = repository.getFixturesByTeamId(4,1)
            Assert.assertEquals(FakeDataSource.fixtures,response)
        }

    @Test
    fun apiTemOverviewRepository_getPlayersByTeamId_verifyPlayersList() =
        runTest {
            val repository = ApiTeamOverviewRepository(FakeFootballApiService())
            val response = repository.getPlayersByTeamId(4)
            Assert.assertEquals(FakeDataSource.players,response)
        }

}