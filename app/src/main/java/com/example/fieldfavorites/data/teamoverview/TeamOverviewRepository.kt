package com.example.fieldfavorites.data.teamoverview

import com.example.fieldfavorites.model.FixtureRow
import com.example.fieldfavorites.model.PlayerRow
import com.example.fieldfavorites.model.Team


/**
 * Repository that gives [FixtureRow] and [PlayerRow] instances from a given source
 * */
interface TeamOverviewRepository {

    /**
     * Returns a [List] of [FixtureRow] for a [Team] by given [teamId].
     * [nextGamesCount] specifies the number of [FixtureRow] fetched.
     * */
    @Throws(Exception::class)
    suspend fun getFixturesByTeamId(teamId: Int, nextGamesCount: Int): List<FixtureRow>

    /**
     * Returns a [List] of [PlayerRow] from a [Team] by given [teamId]
     * */
    @Throws(Exception::class)
    suspend fun getPlayersByTeamId(teamId: Int): List<PlayerRow>

}