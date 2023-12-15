package com.example.fieldfavorites.fakerepositories

import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.model.League

class FakeLeagueRepository:LeagueRepository {
    override fun getTop5Leagues(): List<League> {
        val leagues = listOf(
            League(39,"Premier League","https://media-4.api-sports.io/football/leagues/39.png","https://media-4.api-sports.io/flags/gb.svg"),
            League(140,"La Liga","https://media-4.api-sports.io/football/leagues/140.png","https://media-4.api-sports.io/flags/es.svg"),
            League(61,"Ligue 1","https://media-4.api-sports.io/football/leagues/61.png","https://media-4.api-sports.io/flags/fr.svg"),
            League(135,"Seria A","https://media-4.api-sports.io/football/leagues/135.png","https://media-4.api-sports.io/flags/it.svg"),
            League(78,"Bundesliga","https://media-4.api-sports.io/football/leagues/78.png","https://media-4.api-sports.io/flags/de.svg")
        )

        return leagues
    }
}