package com.example.fieldfavorites.fake.teams

import com.example.fieldfavorites.data.teams.ApiTeamRepository
import com.example.fieldfavorites.data.teams.TeamRepository
import com.example.fieldfavorites.model.ApiResponse
import com.example.fieldfavorites.model.ResponseObject
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.model.TeamFromApi
import com.example.fieldfavorites.network.FootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ApiTeamRepositoryTest {
    private lateinit var repository: TeamRepository
    private lateinit var service:FootballApiService

    @Before
    fun setup() {
        service = mock()
        repository = ApiTeamRepository(service)
    }

    @Test
    fun getAllTeamsFromLeague_returnsAllTeams() = runTest {
        val leagueId = 1
        val fakeApiResponse = ApiResponse(
            response = arrayOf(
                ResponseObject(
                    team = TeamFromApi(id = 1, name = "Team A", logo = "logo_A")
                ),
                ResponseObject(
                    team = TeamFromApi(id = 2, name = "Team B", logo = "logo_B")
                ),
                ResponseObject(
                    team = TeamFromApi(id = 3, name = "Team C", logo = "logo_C")
                )
            )
        )
        val teams = arrayOf(
            Team(1,"Team A","logo_A", leagueId ),
            Team(2,"Team B","logo_B", leagueId ),
            Team(3,"Team C","logo_C", leagueId )

        ).toList()

        whenever(service.getTeamsByLeagueId(leagueId)).thenReturn(fakeApiResponse)

        Assert.assertEquals(teams,repository.getAllTeamsFromLeague(leagueId))
        verify(service).getTeamsByLeagueId(leagueId)
    }
}