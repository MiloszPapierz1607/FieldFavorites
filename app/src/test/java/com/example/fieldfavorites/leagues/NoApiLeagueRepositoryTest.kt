package com.example.fieldfavorites.leagues

import com.example.fieldfavorites.data.leagues.NoApiLeagueRepository
import com.example.fieldfavorites.model.League
import org.junit.Assert.assertEquals
import org.junit.Test

class NoApiLeagueRepositoryTest {
    @Test
    fun noApiLeagueRepository_verifyTop5LeagueList() {
        val leagues = listOf(
            League(
                39,
                "Premier League",
                "https://media-4.api-sports.io/football/leagues/39.png",
                "https://media-4.api-sports.io/flags/gb.svg"
            ),
            League(
                140,
                "La Liga",
                "https://media-4.api-sports.io/football/leagues/140.png",
                "https://media-4.api-sports.io/flags/es.svg"
            ),
            League(
                61,
                "Ligue 1",
                "https://media-4.api-sports.io/football/leagues/61.png",
                "https://media-4.api-sports.io/flags/fr.svg"
            ),
            League(
                135,
                "Seria A",
                "https://media-4.api-sports.io/football/leagues/135.png",
                "https://media-4.api-sports.io/flags/it.svg"
            ),
            League(
                78,
                "Bundesliga",
                "https://media-4.api-sports.io/football/leagues/78.png",
                "https://media-4.api-sports.io/flags/de.svg"
            )
        )
        val repository = NoApiLeagueRepository()
        val response = repository.getTop5Leagues()
        assertEquals(leagues, response)
        assertEquals(5, response.size)
        assertEquals("Premier League", response[0].name)
        assertEquals(135, response[3].id)
    }
}