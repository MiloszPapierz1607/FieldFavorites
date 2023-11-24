package com.example.fieldfavorites.fake.teams

import com.example.fieldfavorites.fake.FakeNetworkDataSource
import com.example.fieldfavorites.model.Team

object FakeDataSource {
    fun create(leagueId: Int): List<Team> {
        return FakeNetworkDataSource.apiResponse.response.asList().map {
            Team(id = it.team.id, name = it.team.name, leagueId = leagueId, logo = it.team.logo)
        }
    }
}