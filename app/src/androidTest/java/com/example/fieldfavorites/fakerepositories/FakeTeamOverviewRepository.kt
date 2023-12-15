package com.example.fieldfavorites.fakerepositories

import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.model.Fixture
import com.example.fieldfavorites.model.FixtureLeague
import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.FixtureTeam
import com.example.fieldfavorites.model.FixtureTeams
import com.example.fieldfavorites.model.FixtureVenue
import com.example.fieldfavorites.model.Player
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.model.StatisitcGoals
import com.example.fieldfavorites.model.StatisticGame
import com.example.fieldfavorites.model.StatisticRow

class FakeTeamOverviewRepository:TeamOverviewRepository {
    override suspend fun getFixturesByTeamId(teamId: Int, nextGamesCount: Int): List<FixtureRow> {
        val fixtures = listOf(
            FixtureRow(
                fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                league = FixtureLeague(round = "Round 1"),
                teams = FixtureTeams(
                    home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                    away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                )
            ),
            FixtureRow(
                fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                league = FixtureLeague(round = "Round 2"),
                teams = FixtureTeams(
                    home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                    away = FixtureTeam(id = 8, name = "Team B", logo = "logo_B")
                )
            ),
            FixtureRow(
                fixture = Fixture(venue = FixtureVenue(name = "Stadium C")),
                league = FixtureLeague(round = "Round 3"),
                teams = FixtureTeams(
                    home = FixtureTeam(id = 10, name = "Team C", logo = "logo_C"),
                    away = FixtureTeam(id = 1, name = "Team A", logo = "logo_A")
                )
            )
        )

        return fixtures
    }

    override suspend fun getPlayersByTeamId(teamId: Int): List<PlayerRow> {
        val playerRows = listOf(
            PlayerRow(
                player = Player(id = 1, name = "Player A", firstname = "First", lastname = "Last", photo = "photo_A"),
                statistics = arrayOf(
                    StatisticRow(
                        games = StatisticGame(appearences = 10, minutes = 900, position = "Forward"),
                        goals = StatisitcGoals(total = 5)
                    )
                )
            ),
            PlayerRow(
                player = Player(id = 2, name = "Player B", firstname = "First", lastname = "Last", photo = "photo_B"),
                statistics = arrayOf(
                    StatisticRow(
                        games = StatisticGame(appearences = 8, minutes = 720, position = "Midfielder"),
                        goals = StatisitcGoals(total = 2)
                    )
                )
            )
        )

        return playerRows
    }

}