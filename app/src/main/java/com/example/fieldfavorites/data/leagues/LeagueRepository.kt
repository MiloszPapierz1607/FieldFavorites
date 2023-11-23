package com.example.fieldfavorites.data.leagues

import com.example.fieldfavorites.model.League

/**
 * Repository that provides [League] from a given data source
* */
interface LeagueRepository {
    /**
     * Returns a [List] of [League] objects. The list contains top 5 football leagues from Europe(Premier League, La Liga,
     * Seria A, Bundesliga and Ligue 1).
     * */
    fun getTop5Leagues(): List<League>
}