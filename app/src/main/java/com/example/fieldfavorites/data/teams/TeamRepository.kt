package com.example.fieldfavorites.data.teams

import com.example.fieldfavorites.model.Team

interface TeamRepository {
    suspend fun getAllTeamsFromLeague(leagueId: Int) : List<Team>
}