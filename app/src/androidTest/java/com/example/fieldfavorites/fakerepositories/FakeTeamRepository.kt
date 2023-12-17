package com.example.fieldfavorites.fakerepositories

import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.model.Team

class FakeTeamRepository : TeamRepository {
    override suspend fun getAllTeamsFromLeague(leagueId: Int): List<Team> {
        val teams = arrayOf(
            Team(1, "Team A", "logo_A", leagueId),
            Team(2, "Team B", "logo_B", leagueId),
            Team(3, "Team C", "logo_C", leagueId)

        ).toList()

        return teams
    }
}