package com.example.fieldfavorites.standings

import com.example.fieldfavorites.data.standings.StandingsApiRepository
import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.model.Standing
import com.example.fieldfavorites.model.StandingAll
import com.example.fieldfavorites.model.StandingApiResponse
import com.example.fieldfavorites.model.StandingLeague
import com.example.fieldfavorites.model.StandingTeam
import com.example.fieldfavorites.model.Standings
import com.example.fieldfavorites.network.FootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StandingsApiReposioryTest {

    private lateinit var standigsRepository: StandingsRepository
    private lateinit var footballApiService: FootballApiService

    @Before
    fun setup() {
        footballApiService = mock()
        standigsRepository = StandingsApiRepository(footballApiService)
    }

    @Test
    fun apiStandingsRepository_getStandingsForLeague_verifyStandingsList() =
        runTest {
            val fakeStandingApiResponse = StandingApiResponse(
                response = arrayOf(
                    Standing(
                        league = StandingLeague(
                            id = 1,
                            name = "Fake League",
                            standings = arrayOf(
                                arrayOf(
                                    Standings(
                                        rank = 1,
                                        points = 20,
                                        goalsDiff = 10,
                                        team = StandingTeam(id = 101, name = "Team A", logo = "logo_A"),
                                        all = StandingAll(played = 10, win = 7, draw = 2, lose = 1)
                                    ),
                                    Standings(
                                        rank = 2,
                                        points = 18,
                                        goalsDiff = 8,
                                        team = StandingTeam(id = 102, name = "Team B", logo = "logo_B"),
                                        all = StandingAll(played = 10, win = 6, draw = 3, lose = 1)
                                    )
                                    // Add more teams if needed
                                )
                            )
                        )
                    )
                )
            )
            val standings = listOf(Standings(
                rank = 1,
                points = 20,
                goalsDiff = 10,
                team = StandingTeam(id = 101, name = "Team A", logo = "logo_A"),
                all = StandingAll(played = 10, win = 7, draw = 2, lose = 1)
            ),
                Standings(
                    rank = 2,
                    points = 18,
                    goalsDiff = 8,
                    team = StandingTeam(id = 102, name = "Team B", logo = "logo_B"),
                    all = StandingAll(played = 10, win = 6, draw = 3, lose = 1)
                ))

            whenever(footballApiService.getStandingsForLeague(1)).thenReturn(fakeStandingApiResponse)

            Assert.assertEquals(standings,standigsRepository.getStandingsForLeague(1))
            verify(footballApiService).getStandingsForLeague(1)
        }
}