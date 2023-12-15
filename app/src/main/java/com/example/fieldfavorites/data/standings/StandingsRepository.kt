package com.example.fieldfavorites.data.standings

import com.example.fieldfavorites.model.Standings

/**
 * Repository that returns [Standings] instances from a given data source
 * */
interface StandingsRepository {
    /**
     * Retrieves a [List] of [Standings] objects from a league with given [leagueId]
     * */
    @Throws(Exception::class)
    suspend fun getStandingsForLeague(leagueId: Int): List<Standings>
}