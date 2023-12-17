package com.example.fieldfavorites.teamoverview

import androidx.lifecycle.SavedStateHandle
import com.example.fieldfavorites.TestDispatcherRule
import com.example.fieldfavorites.data.favorites.FavoriteRepository
import com.example.fieldfavorites.data.standings.StandingsRepository
import com.example.fieldfavorites.data.teamoverview.TeamOverviewRepository
import com.example.fieldfavorites.favorites.FakeOfflineFavoriteRepository
import com.example.fieldfavorites.model.Fixture
import com.example.fieldfavorites.model.FixtureLeague
import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.FixtureTeam
import com.example.fieldfavorites.model.FixtureTeams
import com.example.fieldfavorites.model.FixtureVenue
import com.example.fieldfavorites.model.Player
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.model.StandingAll
import com.example.fieldfavorites.model.StandingTeam
import com.example.fieldfavorites.model.Standings
import com.example.fieldfavorites.model.StatisitcGoals
import com.example.fieldfavorites.model.StatisticGame
import com.example.fieldfavorites.model.StatisticRow
import com.example.fieldfavorites.model.Team
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewApiState
import com.example.fieldfavorites.ui.screens.teamoverview.TeamOverviewViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TeamOverviewViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var teamOverviewRepository: TeamOverviewRepository
    private lateinit var standingsRepository: StandingsRepository
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var viewModel: TeamOverviewViewModel

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle()
        teamOverviewRepository = mock()
        standingsRepository = mock()
        favoriteRepository = FakeOfflineFavoriteRepository()
    }

    @Test
    fun initialization_fetchesAllData() = runTest {
        val teamId = 1
        val teamName = "Team A"
        val leagueId = 1
        val team1 = Team(id = 1, name = teamName, logo = "logo_A", leagueId = leagueId)

        savedStateHandle.set("teamId", teamId)
        savedStateHandle.set("teamName", teamName)

        val standings = listOf(
            Standings(
                rank = 1,
                points = 20,
                goalsDiff = 10,
                team = StandingTeam(id = 101, name = "Team R", logo = "logo_A"),
                all = StandingAll(played = 10, win = 7, draw = 2, lose = 1)
            ),
            Standings(
                rank = 2,
                points = 18,
                goalsDiff = 8,
                team = StandingTeam(id = 102, name = "Team B", logo = "logo_B"),
                all = StandingAll(played = 10, win = 6, draw = 3, lose = 1)
            )
        )
        val playerStats = listOf(
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
        val nextFixture = FixtureRow(
            fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
            league = FixtureLeague(round = "Round 1"),
            teams = FixtureTeams(
                home = FixtureTeam(id = teamId, name = teamName, logo = "logo_A"),
                away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
            )
        )

        whenever(teamOverviewRepository.getFixturesByTeamId(teamId, 1)).thenReturn(
            listOf(
                nextFixture
            )
        )

        favoriteRepository.insertFavoriteTeam(team1)

        whenever(standingsRepository.getStandingsForLeague(leagueId)).thenReturn(standings)

        whenever(teamOverviewRepository.getPlayersByTeamId(teamId)).thenReturn(playerStats)

        viewModel = TeamOverviewViewModel(
            savedStateHandle = savedStateHandle,
            teamOverviewRepository = teamOverviewRepository,
            standingsRepository = standingsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(standings, viewModel.uiState.value.standings)
        Assert.assertEquals(playerStats, viewModel.uiState.value.playerStats)
        Assert.assertEquals(nextFixture, viewModel.uiState.value.nextFixture)
        Assert.assertEquals(
            TeamOverviewApiState.Success(nextFixture, standings, playerStats),
            viewModel.teamOverviewApiState
        )
        verify(teamOverviewRepository).getFixturesByTeamId(teamId, 1)
        verify(teamOverviewRepository).getPlayersByTeamId(teamId)
        verify(standingsRepository).getStandingsForLeague(leagueId)
    }

    @Test
    fun initialization_faultyTeamId_errorApiStateIsSet() = runTest {
        val teamId = null
        savedStateHandle.set("teamId", teamId)
        savedStateHandle.set("teamName", null)

        whenever(teamOverviewRepository.getFixturesByTeamId(0, 1)).thenThrow(Exception::class.java)

        viewModel = TeamOverviewViewModel(
            savedStateHandle = savedStateHandle,
            teamOverviewRepository = teamOverviewRepository,
            standingsRepository = standingsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(TeamOverviewApiState.Error, viewModel.teamOverviewApiState)
        verify(standingsRepository, never()).getStandingsForLeague(any())
        verify(teamOverviewRepository, never()).getPlayersByTeamId(0)

    }

    @Test()
    fun initialization_noFavoriteTeamFound_errorIsThrown() = runTest {
        val teamId = 1
        val teamName = "Team A"
        savedStateHandle.set("teamId", teamId)
        savedStateHandle.set("teamName", teamName)

        whenever(teamOverviewRepository.getFixturesByTeamId(1, 1)).thenReturn(
            listOf(
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 1"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = teamId, name = teamName, logo = "logo_A"),
                        away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                    )
                )
            )
        )

        viewModel = TeamOverviewViewModel(
            savedStateHandle = savedStateHandle,
            teamOverviewRepository = teamOverviewRepository,
            standingsRepository = standingsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(TeamOverviewApiState.Error, viewModel.teamOverviewApiState)
        verify(standingsRepository, never()).getStandingsForLeague(0)
        verify(teamOverviewRepository, never()).getPlayersByTeamId(teamId)
    }

    @Test
    fun initialization_standingsRepositoryThrowsError_ApiStateIsError() = runTest {
        val teamId = 1
        val teamName = "Team A"
        savedStateHandle.set("teamId", teamId)
        savedStateHandle.set("teamName", teamName)

        whenever(teamOverviewRepository.getFixturesByTeamId(1, 1)).thenReturn(
            listOf(
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 1"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = teamId, name = teamName, logo = "logo_A"),
                        away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                    )
                )
            )
        )

        whenever(standingsRepository.getStandingsForLeague(1)).thenThrow(Exception::class.java)

        viewModel = TeamOverviewViewModel(
            savedStateHandle = savedStateHandle,
            teamOverviewRepository = teamOverviewRepository,
            standingsRepository = standingsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(TeamOverviewApiState.Error, viewModel.teamOverviewApiState)
        verify(teamOverviewRepository, never()).getPlayersByTeamId(teamId)
    }

    @Test
    fun initialization_teamOverViewRepositoryGetPlayersThrowsError_ApiStateIsError() = runTest {
        val teamId = 1
        val teamName = "Team A"
        savedStateHandle.set("teamId", teamId)
        savedStateHandle.set("teamName", teamName)

        whenever(teamOverviewRepository.getFixturesByTeamId(1, 1)).thenReturn(
            listOf(
                FixtureRow(
                    fixture = Fixture(venue = FixtureVenue(name = "Stadium A")),
                    league = FixtureLeague(round = "Round 1"),
                    teams = FixtureTeams(
                        home = FixtureTeam(id = teamId, name = teamName, logo = "logo_A"),
                        away = FixtureTeam(id = 2, name = "Team B", logo = "logo_B")
                    )
                )
            )
        )

        whenever(standingsRepository.getStandingsForLeague(1)).thenReturn(
            listOf(
                Standings(
                    rank = 1,
                    points = 20,
                    goalsDiff = 10,
                    team = StandingTeam(id = 101, name = "Team R", logo = "logo_A"),
                    all = StandingAll(played = 10, win = 7, draw = 2, lose = 1)
                ),
                Standings(
                    rank = 2,
                    points = 18,
                    goalsDiff = 8,
                    team = StandingTeam(id = 102, name = "Team B", logo = "logo_B"),
                    all = StandingAll(played = 10, win = 6, draw = 3, lose = 1)
                )
            )
        )

        whenever(teamOverviewRepository.getPlayersByTeamId(teamId)).thenThrow(Exception::class.java)

        viewModel = TeamOverviewViewModel(
            savedStateHandle = savedStateHandle,
            teamOverviewRepository = teamOverviewRepository,
            standingsRepository = standingsRepository,
            favoriteRepository = favoriteRepository
        )

        Assert.assertEquals(TeamOverviewApiState.Error, viewModel.teamOverviewApiState)
    }

}