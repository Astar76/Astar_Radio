package com.astar.astarradio.data.remote

import com.astar.astarradio.core.Result
import com.astar.astarradio.data.RadioData
import javax.inject.Inject

interface RadioCloudDataSource {

    suspend fun fetchRadioStations(): Result<List<RadioData>, Throwable>

    suspend fun fetchRadioStationById(id: Int): Result<RadioData, Throwable>

    class Base @Inject constructor(
        private val service: RadioService,
        private val mapper: CloudRadioDataMapper,
    ) : RadioCloudDataSource {
        override suspend fun fetchRadioStations(): Result<List<RadioData>, Throwable> {
            return try {
                val stations = service.fetchRadioStations().list.map { mapper.map(it) }
                Result.Success(stations)
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }

        override suspend fun fetchRadioStationById(id: Int): Result<RadioData, Throwable> {
            return try {
                val preparedId = String.format("radios/%d", id)
                val station = mapper.map(service.fetchRadioStationById(preparedId))
                Result.Success(station)
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }
    }
}