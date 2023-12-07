package com.example.fieldfavorites.favorites

import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeOfflineFavoriteRepository : FavoriteRepository {

    private val fakeTeams = mutableListOf<Team>()
    private val teamsFlow = MutableStateFlow<List<Team>>(fakeTeams)


    override fun getAllFavoriteTeamsStream(): Flow<List<Team>> {
        return teamsFlow
    }

    override suspend fun getTeamById(teamId: Int): Team? {
        return fakeTeams.find { it.id == teamId }
    }

    override suspend fun insertFavoriteTeam(team: Team) {
        fakeTeams.add(team)
        teamsFlow.value = fakeTeams.toList()
    }

    override suspend fun deleteTeam(teamId: Int) {
        fakeTeams.removeAll { it.id == teamId }
        teamsFlow.value = fakeTeams.toList()
    }
}