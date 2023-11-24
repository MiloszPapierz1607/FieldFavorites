package com.example.fieldfavorites.teamoverview

import com.example.fieldfavorites.fake.FakeNetworkDataSource

object FakeDataSource {
    val fixtures = FakeNetworkDataSource.apiFixtureResonse.response.toList()
    val players= FakeNetworkDataSource.playerApiResponse.response.toList()

}