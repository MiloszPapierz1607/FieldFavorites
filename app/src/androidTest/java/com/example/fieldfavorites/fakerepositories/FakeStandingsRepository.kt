package com.example.fieldfavorites.fakerepositories

import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.model.StandingAll
import com.example.fieldfavorites.model.StandingTeam
import com.example.fieldfavorites.model.Standings

class FakeStandingsRepository : StandingsRepository {
    override suspend fun getStandingsForLeague(leagueId: Int): List<Standings> {
        val standings = listOf(
            Standings(
                rank = 1,
                points = 20,
                goalsDiff = 10,
                team = StandingTeam(id = 101, name = "Team A", logo = "logo_A"),
                all = StandingAll(played = 10, win = 7, draw = 2, lose = 1)
            ),
            Standings(
                rank = 2,
                points = 18,
                goalsDiff = 8,
                team = StandingTeam(id = 102, name = "Team B", logo = "logo_B"),
                all = StandingAll(played = 10, win = 6, draw = 3, lose = 1)
            )
        )

        return standings
    }
}