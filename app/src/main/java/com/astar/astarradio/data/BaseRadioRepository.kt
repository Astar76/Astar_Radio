package com.astar.astarradio.data

import com.astar.astarradio.core.Result
import com.astar.astarradio.data.remote.RadioCloudDataSource
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.domain.RadioStation
import javax.inject.Inject

class BaseRadioRepository @Inject constructor(
    private val cloudDataSource: RadioCloudDataSource,
    private val mapper: RadioStationDomainMapper
): RadioRepository {

    override suspend fun fetchRadioStations(): Result<List<RadioStation>, Throwable> {
        return when (val station = cloudDataSource.fetchRadioStations()) {
            is Result.Success -> Result.Success(station.result.map { mapper.map(it) })
            is Result.Error -> Result.Error(station.result)
        }
    }

    override suspend fun fetchRadioStationById(id: Int): Result<RadioStation, Throwable> {
        return when (val station = cloudDataSource.fetchRadioStationById(id)) {
            is Result.Success -> Result.Success(mapper.map(station.result.radio))
            is Result.Error -> Result.Error(station.result)
        }
    }
}