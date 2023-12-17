package com.example.fieldfavorites.favorites

import com.example.fieldfavorites.data.favorites.FavoriteDao
import com.example.fieldfavorites.data.favorites.OfflineFavoriteRepository
import com.example.fieldfavorites.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class OfflineFavoriteRepositoryTest {

    private lateinit var repository: OfflineFavoriteRepository
    private lateinit var fakeFavoriteDao: FavoriteDao

    @Before
    fun setup() {
        fakeFavoriteDao = mock()
        repository = OfflineFavoriteRepository(fakeFavoriteDao)
    }

    @Test
    fun insertAndRetrieveTeams_returnsAllTeams() = runTest {
        val team1 = Team(id = 1, name = "Team A", logo = "logo_A", leagueId = 1)
        val team2 = Team(id = 2, name = "Team B", logo = "logo_B", leagueId = 2)
        val teams = listOf(team1, team2)

        val teamsFlow = MutableStateFlow<List<Team>>(emptyList())
        whenever(fakeFavoriteDao.insert(team1)).then {
            teamsFlow.value = listOf(team1)
            Unit
        }
        whenever(fakeFavoriteDao.insert(team2)).then {
            teamsFlow.value = listOf(team1, team2)
            Unit
        }
        whenever(fakeFavoriteDao.getAllFavorites()).thenReturn(teamsFlow)

        repository.insertFavoriteTeam(team1)
        repository.insertFavoriteTeam(team2)

        val result = repository.getAllFavoriteTeamsStream().first()
        Assert.assertEquals(teams, result)

        verify(fakeFavoriteDao).insert(team1)
        verify(fakeFavoriteDao).insert(team2)
        verify(fakeFavoriteDao).getAllFavorites()
    }

    @Test
    fun getTeamById_existingId_returnsTeam() = runTest {
        val team1 = Team(id = 1, name = "Team A", logo = "logo_A", leagueId = 1)

        val teamsFlow = MutableStateFlow<List<Team>>(emptyList())

        whenever(fakeFavoriteDao.insert(team1)).then {
            teamsFlow.value = listOf(team1)
            Unit
        }

        whenever(fakeFavoriteDao.getTeamById(1)).thenReturn(team1)

        repository.insertFavoriteTeam(team1)
        val teamFromRepo = repository.getTeamById(1)

        Assertions.assertEquals(team1, teamFromRepo)

        verify(fakeFavoriteDao).insert(team1)
        verify(fakeFavoriteDao).getTeamById(1)
    }

    @Test
    fun getTeamById_nonExistingId_returnsTeam() = runTest {
        whenever(fakeFavoriteDao.getTeamById(45)).thenReturn(null)

        val teamFromRepo = repository.getTeamById(1)

        Assertions.assertNull(teamFromRepo)

        verify(fakeFavoriteDao).getTeamById(1)
    }

    @Test
    fun deleteTeamById_existingId_removesTeam() = runTest {
        // Given a list of teams
        val team1 = Team(id = 1, name = "Team A", logo = "logo_A", leagueId = 1)
        val team2 = Team(id = 2, name = "Team B", logo = "logo_B", leagueId = 2)

        // Mock the behavior of insertFavoriteTeam and getAllFavorites flow in the fakeFavoriteDao
        val teamsFlow = MutableStateFlow<List<Team>>(emptyList())
        whenever(fakeFavoriteDao.insert(team1)).then {
            teamsFlow.value = listOf(team1)
            Unit
        }
        whenever(fakeFavoriteDao.insert(team2)).then {
            teamsFlow.value = listOf(team1, team2)
            Unit
        }
        whenever(fakeFavoriteDao.getAllFavorites()).thenReturn(teamsFlow)

        // When inserting teams into the repository
        repository.insertFavoriteTeam(team1)
        repository.insertFavoriteTeam(team2)

        // Then verify that there are initially 2 teams
        Assert.assertEquals(2, repository.getAllFavoriteTeamsStream().first().size)

        whenever(fakeFavoriteDao.delete(1)).thenReturn(Unit)

        // When deleting a team by ID
        repository.deleteTeam(1)

        whenever(fakeFavoriteDao.getAllFavorites()).thenReturn(MutableStateFlow(listOf(team2)))

        // Then retrieve all teams and verify that one team is removed
        Assert.assertEquals(1, repository.getAllFavoriteTeamsStream().first().size)
        Assert.assertEquals(team2, repository.getAllFavoriteTeamsStream().first().first())

        verify(fakeFavoriteDao).delete(1)
        verify(fakeFavoriteDao).insert(team1)
        verify(fakeFavoriteDao).insert(team2)
        verify(fakeFavoriteDao, times(3)).getAllFavorites()
    }

}