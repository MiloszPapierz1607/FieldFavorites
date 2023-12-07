package com.example.fieldfavorites.leagues

import com.example.fieldfavorites.TestDispatcherRule
import com.example.fieldfavorites.data.leagues.LeagueRepository
import com.example.fieldfavorites.model.League
import com.example.fieldfavorites.ui.screens.leagues.LeagueViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LeagueViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: LeagueViewModel
    private lateinit var fakeRepository: LeagueRepository
    @Before
    fun setup() {
        fakeRepository = mock()
    }

    @Test
    fun initializationFetchedListOfLeagues() {
        val leagues = listOf(
            League(39,"Premier League","https://media-4.api-sports.io/football/leagues/39.png","https://media-4.api-sports.io/flags/gb.svg"),
            League(140,"La Liga","https://media-4.api-sports.io/football/leagues/140.png","https://media-4.api-sports.io/flags/es.svg"),
            League(61,"Ligue 1","https://media-4.api-sports.io/football/leagues/61.png","https://media-4.api-sports.io/flags/fr.svg"),
            League(135,"Seria A","https://media-4.api-sports.io/football/leagues/135.png","https://media-4.api-sports.io/flags/it.svg"),
            League(78,"Bundesliga","https://media-4.api-sports.io/football/leagues/78.png","https://media-4.api-sports.io/flags/de.svg")
        )

        whenever(fakeRepository.getTop5Leagues()).thenReturn(leagues)

        viewModel = LeagueViewModel(fakeRepository)

        Assert.assertEquals(leagues,viewModel.uiState.value.leagues)
        verify(fakeRepository).getTop5Leagues()
    }

}