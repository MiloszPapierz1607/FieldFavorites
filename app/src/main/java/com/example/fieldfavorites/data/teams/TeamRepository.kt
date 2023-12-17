package com.example.fieldfavorites.data.teams

import com.example.fieldfavorites.model.League
import com.example.fieldfavorites.model.Team

/**
 * Repository that gives [Team] instances from a given source
 * */
interface TeamRepository {
    /**
     * Returns a [List] of [Team] objects for a [League] by given [leagueId]
     * */
    @Throws(Exception::class)
    suspend fun getAllTeamsFromLeague(leagueId: Int): List<Team>
}