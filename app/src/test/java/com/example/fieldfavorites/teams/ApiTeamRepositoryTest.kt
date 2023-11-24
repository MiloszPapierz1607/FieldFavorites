package com.example.fieldfavorites.fake.teams

import com.example.fieldfavorites.data.teams.ApiTeamRepository
import com.example.fieldfavorites.fake.FakeFootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class ApiTeamRepositoryTest {


    @RunWith(Parameterized::class)
    class ApiParameterizedRepositoryTest(private val leagueId: Int) {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun leagueIds() = listOf(
                51,
                71,
                87
            )
        }

        @Test
        fun apiTeamRepository_getAllTeamsFromLeague_verifyCorrectLeagueIdConversion() =
            runTest {
                val repository = ApiTeamRepository(FakeFootballApiService())
                val response = repository.getAllTeamsFromLeague(leagueId)
                Assert.assertEquals(FakeDataSource.create(leagueId),response)
                Assert.assertEquals(leagueId,response[0].leagueId)
            }
    }

    class ApiNoParamsRepositoryTest {
        @Test
        fun apiTeamRepository_getAllTeamsFromLeague_verifyList() =
            runTest {
                val repository = ApiTeamRepository(FakeFootballApiService())
                Assert.assertEquals(FakeDataSource.create(4),repository.getAllTeamsFromLeague(4))
            }
    }

}