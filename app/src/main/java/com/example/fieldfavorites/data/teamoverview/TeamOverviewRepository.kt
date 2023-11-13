package com.example.fieldfavorites.data.teamoverview

import com.example.fieldfavorites.model.FixtureRow


interface TeamOverviewRepository {

    suspend fun getFixturesByTeamId(teamId: Int,nextGamesCount: Int) : List<FixtureRow>

}