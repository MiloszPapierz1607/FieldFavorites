package com.example.fieldfavorites.fake.standings

import com.example.fieldfavorites.fake.FakeNetworkDataSource

object FakeDataSource {
    val standings = FakeNetworkDataSource.standingApiResponse.response[0].league.standings[0].toList()
}