package com.astar.astarradio.data.local

import com.astar.astarradio.data.RadioData
import com.astar.astarradio.domain.RadioDomain
import javax.inject.Inject

interface RadioLocalDataSource {

    suspend fun fetchAll(): List<RadioData>

    suspend fun fetchFavorites(): List<RadioData>

    suspend fun insertAll(radioStations: List<RadioData>)

    suspend fun clearAll()

    suspend fun addToFavorite(radio: RadioData)

    class Base @Inject constructor(
        private val dao: RadioStationsDao,
        private val mapper: LocalRadioDataMapper
    ) : RadioLocalDataSource {

        override suspend fun fetchAll(): List<RadioData> {
            return dao.fetchAll().map { mapper.map(it) }
        }

        override suspend fun fetchFavorites(): List<RadioData> {
            return dao.fetchFavorites().map { mapper.map(it) }
        }

        override suspend fun insertAll(radioStations: List<RadioData>) {
            dao.insertAll(radioStations.map { mapper.map(it) })
        }

        override suspend fun clearAll() {
            dao.deleteAll()
        }

        override suspend fun addToFavorite(radio: RadioData) {
            val newFavoriteValue = !radio.isFavorite
            dao.addToFavorite(radio.name, newFavoriteValue)
        }
    }

}