package com.example.fieldfavorites.data.teams

import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.network.FootballApiService

class ApiTeamRepository(
    private val footballApiService: FootballApiService
) : TeamRepository{
    override suspend fun getAllTeamsFromLeague(leagueId: Int) : List<Team> {
       val leaguesResponse =  footballApiService.getLeagues(leagueId)
        return leaguesResponse.response.asList().map {
            it.team
        }
    }
}