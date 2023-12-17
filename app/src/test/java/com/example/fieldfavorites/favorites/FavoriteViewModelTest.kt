package com.example.fieldfavorites.favorites

import com.example.fieldfavorites.TestDispatcherRule
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.ui.screens.favorites.FavoriteViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var fakeRepository: FavoriteRepository

    @Before
    fun setup() {
        fakeRepository = FakeOfflineFavoriteRepository()
        viewModel = FavoriteViewModel(fakeRepository)
    }

    @Test
    fun initializeWithNoFavorites_returnsEmptyList() = runTest {
        Assert.assertEquals(emptyList<Team>(), viewModel.uiState.value.favoriteTeams)
    }

    @Test
    fun initializeWithFavorites_returnsListWithFavorites() = runTest {
        insertTeams()

        Assert.assertEquals(2, viewModel.uiState.value.favoriteTeams.size)
        Assert.assertEquals("Team A", viewModel.uiState.value.favoriteTeams[0].name)
        Assert.assertEquals("Team B", viewModel.uiState.value.favoriteTeams[1].name)
    }

    @Test
    fun deleteFavoriteTeamWithExistingId_deletesFavoriteTeam() = runTest {
        insertTeams()

        viewModel.removeFavoriteTeam(1)

        Assert.assertEquals(1, viewModel.uiState.value.favoriteTeams.size)
        Assert.assertEquals(2, viewModel.uiState.value.favoriteTeams.get(0).id)
        Assert.assertEquals("Team B", viewModel.uiState.value.favoriteTeams.get(0).name)
    }

    @Test
    fun deleteFavoriteTeamWithNonExistingId_doesNotDeleteAnyTeam() = runTest {
        insertTeams()

        viewModel.removeFavoriteTeam(80)

        Assert.assertEquals(2, viewModel.uiState.value.favoriteTeams.size)
        Assert.assertEquals("Team A", viewModel.uiState.value.favoriteTeams[0].name)
        Assert.assertEquals("Team B", viewModel.uiState.value.favoriteTeams[1].name)
    }

    private suspend fun insertTeams() {
        val team1 = Team(id = 1, name = "Team A", logo = "logo_A", leagueId = 1)
        val team2 = Team(id = 2, name = "Team B", logo = "logo_B", leagueId = 2)

        fakeRepository.insertFavoriteTeam(team1)
        fakeRepository.insertFavoriteTeam(team2)
    }
}