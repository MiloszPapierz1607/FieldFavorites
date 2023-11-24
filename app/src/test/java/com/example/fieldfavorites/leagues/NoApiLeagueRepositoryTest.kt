package com.example.fieldfavorites.fake.leagues

import com.example.fieldfavorites.data.leagues.NoApiLeagueRepository
import org.junit.Assert.assertEquals
import org.junit.Test
class NoApiLeagueRepositoryTest {
    @Test
    fun noApiLeagueRepository_verifyTop5LeagueList() {
        val repository = NoApiLeagueRepository()
        val response = repository.getTop5Leagues()
        assertEquals(FakeLeagueDataSource.leagues,response)
        assertEquals(5,response.size)
        assertEquals("Premier League",response[0].name)
        assertEquals(135,response[3].id)
    }
}