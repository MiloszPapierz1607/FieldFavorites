package com.example.fieldfavorites.teamoverview

import com.example.fieldfavorites.data.teamoverview.ApiTeamOverviewRepository
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.model.ApiFixtureResponse
import com.example.fieldfavorites.model.Fixture
import com.example.fieldfavorites.model.FixtureLeague
import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.FixtureTeam
import com.example.fieldfavorites.model.FixtureTeams
import com.example.fieldfavorites.model.FixtureVenue
import com.example.fieldfavorites.model.Paging
import com.example.fieldfavorites.model.Player
import com.example.fieldfavorites.model.PlayerApiResponse
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.model.StatisitcGoals
import com.example.fieldfavorites.model.StatisticGame
import com.example.fieldfavorites.model.StatisticRow
import com.example.fieldfavorites.network.FootballApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ApiTeamOverviewRepositoryTest {

    private lateinit var repository: TeamOverviewRepository
    private lateinit var service: FootballApiService

    @Before
    fun setup() {
        service = mock()
        repository = ApiTeamOverviewRepository(service)
    }

    @Test
    fun apiTeamOverviewRepository_getFixturesByTeamIdWithOneFixture_verifyFixturesList() =
        runTest {
            val gameCount = 1
            val fakeApiFixtureResponse = ApiFixtureResponse(
                response = arrayOf(
                    FixtureRow(
                        fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                        league = FixtureLeague(round = "Round 1"),
                        teams = FixtureTeams(
                            home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                            away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                        )
                    )
                )
            )

            val fixtures = listOf(
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 1"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                        away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                    )
                )
            )

            whenever(service.getFixturesByTeamId(1, gameCount)).thenReturn(fakeApiFixtureResponse)

            val response = repository.getFixturesByTeamId(1, gameCount)

            Assert.assertEquals(fixtures, response)
            verify(service).getFixturesByTeamId(1, gameCount)
        }

    @Test
    fun apiTeamOverviewRepository_getFixturesByTeamIdWithMoreFixtures_verifyFixturesList() =
        runTest {
            val gameCount = 3
            val fakeApiFixtureResponse = ApiFixtureResponse(
                response = arrayOf(
                    FixtureRow(
                        fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                        league = FixtureLeague(round = "Round 1"),
                        teams = FixtureTeams(
                            home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                            away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                        )
                    ),
                    FixtureRow(
                        fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                        league = FixtureLeague(round = "Round 2"),
                        teams = FixtureTeams(
                            home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                            away = FixtureTeam(id = 8, name = "Team B", logo = "logo_B")
                        )
                    ),
                    FixtureRow(
                        fixture = Fixture(venue = FixtureVenue(name = "Stadium C")),
                        league = FixtureLeague(round = "Round 3"),
                        teams = FixtureTeams(
                            home = FixtureTeam(id = 10, name = "Team C", logo = "logo_C"),
                            away = FixtureTeam(id = 1, name = "Team A", logo = "logo_A")
                        )
                    )
                )
            )

            val fixtures = listOf(
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 1"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                        away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                    )
                ),
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 2"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = 1, name = "Team A", logo = "logo_A"),
                        away = FixtureTeam(id = 8, name = "Team B", logo = "logo_B")
                    )
                ),
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium C")),
                    league = FixtureLeague(round = "Round 3"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = 10, name = "Team C", logo = "logo_C"),
                        away = FixtureTeam(id = 1, name = "Team A", logo = "logo_A")
                    )
                )
            )

            whenever(service.getFixturesByTeamId(1, gameCount)).thenReturn(fakeApiFixtureResponse)

            val response = repository.getFixturesByTeamId(1, gameCount)

            Assert.assertEquals(fixtures, response)
            Assert.assertEquals(gameCount, response.size)
            verify(service).getFixturesByTeamId(1, gameCount)
        }

    @Test
    fun apiTemOverviewRepository_getPlayersByTeamIdOneRequest_returnsPlayersList() =
        runTest {
            val teamId = 1
            whenever(service.getPlayersByTeamId(teamId, 1)).thenReturn(
                PlayerApiResponse(
                    paging = Paging(current = 1, total = 1),
                    response = arrayOf(
                        PlayerRow(
                            player = Player(
                                id = 1,
                                name = "Player A",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_A"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 10,
                                        minutes = 900,
                                        position = "Forward"
                                    ),
                                    goals = StatisitcGoals(total = 5)
                                )
                            )
                        ),
                        PlayerRow(
                            player = Player(
                                id = 2,
                                name = "Player B",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_B"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 8,
                                        minutes = 720,
                                        position = "Midfielder"
                                    ),
                                    goals = StatisitcGoals(total = 2)
                                )
                            )
                        ),

                        )
                )
            )

            val playerRows = listOf(
                PlayerRow(
                    player = Player(
                        id = 1,
                        name = "Player A",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_A"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 10,
                                minutes = 900,
                                position = "Forward"
                            ),
                            goals = StatisitcGoals(total = 5)
                        )
                    )
                ),
                PlayerRow(
                    player = Player(
                        id = 2,
                        name = "Player B",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_B"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 8,
                                minutes = 720,
                                position = "Midfielder"
                            ),
                            goals = StatisitcGoals(total = 2)
                        )
                    )
                )
            )

            val response = repository.getPlayersByTeamId(teamId)

            Assert.assertEquals(2, response.size)
            Assert.assertEquals(playerRows[0].player, response[0].player)
            Assert.assertArrayEquals(playerRows[0].statistics, response[0].statistics)
            verify(service).getPlayersByTeamId(teamId, 1)
        }

    @Test
    fun apiTemOverviewRepository_getPlayersByTeamIdTwoRequest_returnsPlayersList() =
        runTest {
            val teamId = 1
            whenever(service.getPlayersByTeamId(teamId, 1)).thenReturn(
                PlayerApiResponse(
                    paging = Paging(current = 1, total = 2),
                    response = arrayOf(
                        PlayerRow(
                            player = Player(
                                id = 1,
                                name = "Player A",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_A"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 10,
                                        minutes = 900,
                                        position = "Forward"
                                    ),
                                    goals = StatisitcGoals(total = 5)
                                )
                            )
                        ),
                        PlayerRow(
                            player = Player(
                                id = 2,
                                name = "Player B",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_B"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 8,
                                        minutes = 720,
                                        position = "Midfielder"
                                    ),
                                    goals = StatisitcGoals(total = 2)
                                )
                            )
                        ),

                        )
                )
            )

            whenever(service.getPlayersByTeamId(teamId, 2)).thenReturn(
                PlayerApiResponse(
                    paging = Paging(current = 2, total = 1),
                    response = arrayOf(
                        PlayerRow(
                            player = Player(
                                id = 3,
                                name = "Player C",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_C"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 10,
                                        minutes = 900,
                                        position = "Forward"
                                    ),
                                    goals = StatisitcGoals(total = 5)
                                )
                            )
                        ),
                        PlayerRow(
                            player = Player(
                                id = 4,
                                name = "Player D",
                                firstname = "First",
                                lastname = "Last",
                                photo = "photo_D"
                            ),
                            statistics = arrayOf(
                                StatisticRow(
                                    games = StatisticGame(
                                        appearences = 8,
                                        minutes = 720,
                                        position = "Midfielder"
                                    ),
                                    goals = StatisitcGoals(total = 2)
                                )
                            )
                        ),

                        )
                )
            )

            val playerRows = listOf(
                PlayerRow(
                    player = Player(
                        id = 1,
                        name = "Player A",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_A"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 10,
                                minutes = 900,
                                position = "Forward"
                            ),
                            goals = StatisitcGoals(total = 5)
                        )
                    )
                ),
                PlayerRow(
                    player = Player(
                        id = 2,
                        name = "Player B",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_B"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 8,
                                minutes = 720,
                                position = "Midfielder"
                            ),
                            goals = StatisitcGoals(total = 2)
                        )
                    )
                ),
                PlayerRow(
                    player = Player(
                        id = 3,
                        name = "Player C",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_C"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 10,
                                minutes = 900,
                                position = "Forward"
                            ),
                            goals = StatisitcGoals(total = 5)
                        )
                    )
                ),
                PlayerRow(
                    player = Player(
                        id = 4,
                        name = "Player D",
                        firstname = "First",
                        lastname = "Last",
                        photo = "photo_D"
                    ),
                    statistics = arrayOf(
                        StatisticRow(
                            games = StatisticGame(
                                appearences = 8,
                                minutes = 720,
                                position = "Midfielder"
                            ),
                            goals = StatisitcGoals(total = 2)
                        )
                    )
                )
            )

            val response = repository.getPlayersByTeamId(teamId)

            Assert.assertEquals(4, response.size)
            Assert.assertEquals(playerRows[0].player, response[0].player)
            Assert.assertArrayEquals(playerRows[0].statistics, response[0].statistics)
            verify(service).getPlayersByTeamId(teamId, 1)
            verify(service).getPlayersByTeamId(teamId, 1)
        }

}