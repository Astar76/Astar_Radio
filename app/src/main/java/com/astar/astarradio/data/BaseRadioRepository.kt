package com.astar.astarradio.data

import com.astar.astarradio.core.Result
import com.astar.astarradio.data.local.RadioLocalDataSource
import com.astar.astarradio.data.remote.RadioCloudDataSource
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.domain.RadioDomain
import com.astar.astarradio.domain.RadioDomainMapper
import javax.inject.Inject

class BaseRadioRepository @Inject constructor(
    private val cloudDataSource: RadioCloudDataSource,
    private val localDataSource: RadioLocalDataSource,
    private val mapper: RadioDomainMapper,
) : RadioRepository {

    override suspend fun fetchRadioStations(): Result<List<RadioDomain>, Throwable> {
        val localRadioData = localDataSource.fetchAll()
        return if (localRadioData.isNotEmpty()) {
            Result.Success(localRadioData.map { mapper.map(it) })
        } else {
            when (val remoteRadioData = cloudDataSource.fetchRadioStations()) {
                is Result.Success -> {
                    val result = remoteRadioData.result
                    localDataSource.insertAll(result)
                    Result.Success(result.map { mapper.map(it) })
                }
                is Result.Error -> Result.Error(remoteRadioData.result)
            }
        }
    }

    override suspend fun fetchFavoriteRadioStations(): Result<List<RadioDomain>, Throwable> {
        return Result.Success(localDataSource.fetchFavorites().map { mapper.map(it) })
    }

    override suspend fun fetchRadioStationById(id: Int): Result<RadioDomain, Throwable> {
        return when (val station = cloudDataSource.fetchRadioStationById(id)) {
            is Result.Success -> Result.Success(mapper.map(station.result))
            is Result.Error -> Result.Error(station.result)
        }
    }

    override suspend fun addToFavorite(radio: RadioDomain) {
        localDataSource.addToFavorite(mapper.map(radio))
    }
}