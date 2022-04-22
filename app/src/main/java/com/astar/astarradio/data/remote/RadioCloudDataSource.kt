package com.astar.astarradio.data.remote

import com.astar.astarradio.core.Result
import javax.inject.Inject

interface RadioCloudDataSource {

    suspend fun fetchRadioStations(): Result<List<RadioStationCloud>, Throwable>

    suspend fun fetchRadioStationById(id: Int): Result<SimpleRadioStationCloud, Throwable>

    class Base @Inject constructor(private val service: RadioService): RadioCloudDataSource {
        override suspend fun fetchRadioStations(): Result<List<RadioStationCloud>, Throwable> {
            return try {
                val stations = service.fetchRadioStations().list
                Result.Success(stations)
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }

        override suspend fun fetchRadioStationById(id: Int): Result<SimpleRadioStationCloud, Throwable> {
            return try {
                val preparedId = String.format("radios/%d", id)
                val station = service.fetchRadioStationById(preparedId)
                Result.Success(station)
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }
    }
}