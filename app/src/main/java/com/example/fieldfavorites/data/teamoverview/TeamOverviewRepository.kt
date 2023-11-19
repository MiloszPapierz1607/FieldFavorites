package com.example.fieldfavorites.data.teamoverview

import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.PlayerRow


interface TeamOverviewRepository {

    suspend fun getFixturesByTeamId(teamId: Int,nextGamesCount: Int) : List<FixtureRow>
    suspend fun getPlayersByTeamId(teamId: Int): List<PlayerRow>

}