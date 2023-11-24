package com.example.fieldfavorites.fake

import com.example.fieldfavorites.model.ApiFixtureResponse
import com.example.fieldfavorites.model.ApiResponse
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
import com.example.fieldfavorites.model.ResponseObject
import com.example.fieldfavorites.model.Standing
import com.example.fieldfavorites.model.StandingAll
import com.example.fieldfavorites.model.StandingApiResponse
import com.example.fieldfavorites.model.StandingLeague
import com.example.fieldfavorites.model.StandingTeam
import com.example.fieldfavorites.model.Standings
import com.example.fieldfavorites.model.StatisitcGoals
import com.example.fieldfavorites.model.StatisticGame
import com.example.fieldfavorites.model.StatisticRow
import com.example.fieldfavorites.model.TeamFromApi

object FakeNetworkDataSource {
    //ApiResponse
    private const val idOne = 529
    private const val nameOne = "FC Barcelona"
    private const val imgOne = "url.one"
    private const val idTwo = 533
    private const val nameTwo = "Villarreal"
    private const val imgTwo = "url.two"
    private const val idThree = 536
    private const val nameThree = "Sevilla"
    private const val imgThree = "url.three"
    //ApiFixtureResponse
    private const val venueOne = "Santiago Bernabeu"
    private const val roundOne = "Matchday 14"
    //StandingApiResponse
    private const val leagueIdOne = 50
    private const val leagueNameOne = "La liga"
    private const val standingRankOne = 1
    private const val standingPointsOne = 15
    private const val standingGoalsDiffOne = 25
    private const val standingPlayedOne = 5
    private const val standingWinOne = 5
    private const val standingLoseOne= 0
    private const val standingDrawOne = 0
    private const val standingRankTwo = 2
    private const val standingPointsTwo = 12
    private const val standingGoalsDiffTwo = 20
    private const val standingPlayedTwo = 5
    private const val standingWinTwo = 4
    private const val standingLoseTwo= 1
    private const val standingDrawTwo = 0
    //PlayerApiResponse
    private const val currentPaging = 1
    private const val totalPaging = 1
    private const val playerIdOne = 212
    private const val playerNameOne = "Vinicius Junior"
    private const val playerFirstNameOne = "Vinicius Jose Paixao"
    private const val playerLastNameOne = "de Oliveira Junior"
    private const val playerImgOne = "url.one"
    private const val playerAppearancesOne = 5
    private const val playerMinutesOne = 450
    private const val playerPositionOne = "Forward"
    private const val playerTotalOne = 5
    private const val playerIdTwo = 412
    private const val playerNameTwo = "Bellingham"
    private const val playerFirstNameTwo = "Jude"
    private const val playerLastNameTwo = "Bellingham"
    private const val playerImgTwo = "url.Two"
    private const val playerAppearancesTwo = 5
    private const val playerMinutesTwo = 400
    private const val playerPositionTwo = "Middfielder"
    private const val playerTotalTwo = 8



    val apiResponse = ApiResponse(
        response = listOf(
            ResponseObject(
                TeamFromApi(idOne, nameOne, imgOne)
            ),
            ResponseObject(
                TeamFromApi(idTwo, nameTwo, imgTwo)
            ),
            ResponseObject(
                TeamFromApi(idThree, nameThree, imgThree)
            )
        ).toTypedArray()
    )

    val apiFixtureResonse = ApiFixtureResponse(
        response = listOf(
            FixtureRow(
                fixture = Fixture(
                    venue = FixtureVenue(venueOne)
                ),
                league = FixtureLeague(roundOne),
                teams = FixtureTeams(
                    home = FixtureTeam(idOne, nameOne, imgOne),
                    away = FixtureTeam(idTwo, nameTwo, imgTwo)
                )
            )
        ).toTypedArray()
    )

    val standingApiResponse = StandingApiResponse(
        response = listOf(
            Standing(StandingLeague(leagueIdOne, leagueNameOne,Array(1) {
                Array(2) {
                    Standings(standingRankOne, standingPointsOne, standingGoalsDiffOne, StandingTeam(
                        idOne, nameOne, imgOne), StandingAll(standingPlayedOne, standingWinOne,
                        standingDrawOne, standingLoseOne)
                    )
                    Standings(standingRankTwo, standingPointsTwo, standingGoalsDiffTwo, StandingTeam(idTwo,
                        nameTwo, imgTwo), StandingAll(
                        standingPlayedTwo, standingWinTwo,
                        standingDrawTwo, standingLoseTwo)
                    )
                }
            }))
        ).toTypedArray()
    )

    val playerApiResponse = PlayerApiResponse(
        Paging(currentPaging, totalPaging),
        listOf(
            PlayerRow(
                Player(playerIdOne, playerNameOne, playerFirstNameOne, playerLastNameOne,
                    playerImgOne),
                listOf(StatisticRow(
                    StatisticGame(playerAppearancesOne, playerMinutesOne, playerPositionOne),
                    StatisitcGoals(playerTotalOne)
                ))
                    .toTypedArray()
            ),
            PlayerRow(
                Player(playerIdTwo, playerNameTwo, playerFirstNameTwo, playerLastNameTwo,
                    playerImgTwo),
                listOf(StatisticRow(
                    StatisticGame(playerAppearancesTwo, playerMinutesTwo, playerPositionTwo),
                    StatisitcGoals(playerTotalTwo)
                ))
                    .toTypedArray()
            )

        ).toTypedArray()
    )
}