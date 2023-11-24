package com.example.fieldfavorites.fake.standings

import com.example.fieldfavorites.data.standings.StandingsApiRepository
import com.example.fieldfavorites.fake.FakeFootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class StandingsApiReposioryTest {
    @Test
    fun apiStandingsRepository_getStandingsForLeague_verifyStandingsList() =
        runTest {
            val repository = StandingsApiRepository(FakeFootballApiService())
            val response = repository.getStandingsForLeague(4)
            Assert.assertEquals(FakeDataSource.standings,response)
        }
}