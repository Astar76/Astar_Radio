package com.astar.astarradio.data.remote

import javax.inject.Inject

interface RadioDataSource {

    suspend fun fetchRadioStations(): List<RadioStationCloud>

    suspend fun fetchRadioStationById(id: Int): SimpleRadioStationCloud

    class Base @Inject constructor(private val service: RadioService): RadioDataSource {
        override suspend fun fetchRadioStations(): List<RadioStationCloud> =
            service.fetchRadioStations().list

        override suspend fun fetchRadioStationById(id: Int): SimpleRadioStationCloud {
            val preparedId = String.format("radios/%d", id)
            return service.fetchRadioStationById(preparedId)
        }
    }
}