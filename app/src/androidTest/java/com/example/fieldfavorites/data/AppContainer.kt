package com.example.fieldfavorites.data

import android.content.Context
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.fakerepositories.FakeFavoriteRepository
import com.example.fieldfavorites.fakerepositories.FakeLeagueRepository
import com.example.fieldfavorites.fakerepositories.FakeStandingsRepository
import com.example.fieldfavorites.fakerepositories.FakeTeamOverviewRepository
import com.example.fieldfavorites.fakerepositories.FakeTeamRepository


class AppDataContainer(private val context:Context):AppContainer {
    private val fakeFavoriteRepository = FakeFavoriteRepository()

    override val leagueRepository: LeagueRepository by lazy {
        FakeLeagueRepository()
    }

    override val teamRepository: TeamRepository by lazy {
        FakeTeamRepository()
    }

    override val teamOverviewRepository: TeamOverviewRepository by lazy {
        FakeTeamOverviewRepository()
    }

    override val standingsRepository: StandingsRepository by lazy {
        FakeStandingsRepository()
    }

    //Use the same instance for testing the flow. If different instances provided the favorites data might not be correct
    override val favoriteRepository: FavoriteRepository by lazy {
        FakeFavoriteRepository()
    }

}