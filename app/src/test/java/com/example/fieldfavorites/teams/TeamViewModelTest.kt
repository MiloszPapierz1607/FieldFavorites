package com.example.fieldfavorites.teams

import androidx.lifecycle.SavedStateHandle
import com.example.fieldfavorites.TestDispatcherRule
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.favorites.FakeOfflineFavoriteRepository
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.ui.screens.teams.TeamApiState
import com.example.fieldfavorites.ui.screens.teams.TeamViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TeamViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: TeamViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var teamsRepository: TeamRepository
    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setup() {
        favoriteRepository = FakeOfflineFavoriteRepository()
        teamsRepository = mock()
        savedStateHandle = SavedStateHandle()
    }

    @Test
    fun initializing_returnsListOfTeams() = runTest {
        val leagueId = 1
        savedStateHandle.set("itemId", leagueId)
        val teams = listOf(
            Team(1, "Team A", "logo_A", leagueId),
            Team(2, "Team B", "logo_B", leagueId),
            Team(3, "Team C", "logo_C", leagueId)
        )

        whenever(teamsRepository.getAllTeamsFromLeague(leagueId)).thenReturn(teams)
        viewModel = TeamViewModel(
            savedStateHandle = savedStateHandle,
            teamsRepository = teamsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(teams, viewModel.uiState.value.teams)
        Assert.assertEquals(TeamApiState.Success(teams), viewModel.teamApiState)
        verify(teamsRepository).getAllTeamsFromLeague(leagueId)
    }

    @Test
    fun initializing_apiThrowsError_apiStateIsSetToError() = runTest {
        val leagueId = 1
        savedStateHandle.set("itemId", leagueId)
        val teams = listOf(
            Team(1, "Team A", "logo_A", leagueId),
            Team(2, "Team B", "logo_B", leagueId),
            Team(3, "Team C", "logo_C", leagueId)
        )

        whenever(teamsRepository.getAllTeamsFromLeague(leagueId)).thenThrow(Exception::class.java)
        viewModel = TeamViewModel(
            savedStateHandle = savedStateHandle,
            teamsRepository = teamsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(TeamApiState.Error, viewModel.teamApiState)
        verify(teamsRepository).getAllTeamsFromLeague(leagueId)
    }

    @Test
    fun addingFavoriteTeam_addsNewTeam() = runTest {
        val leagueId = 1
        savedStateHandle.set("itemId", leagueId)
        val team = Team(1, "Team A", "logo_A", leagueId)

        whenever(teamsRepository.getAllTeamsFromLeague(leagueId)).thenReturn(emptyList<Team>())

        viewModel = TeamViewModel(
            savedStateHandle = savedStateHandle,
            teamsRepository = teamsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(
            emptyList<Team>(),
            favoriteRepository.getAllFavoriteTeamsStream().first()
        )
        viewModel.insertFavoriteTeam(team)
        Assert.assertEquals(1, favoriteRepository.getAllFavoriteTeamsStream().first().size)
        Assert.assertEquals(team, favoriteRepository.getAllFavoriteTeamsStream().first().get(0))
    }
}