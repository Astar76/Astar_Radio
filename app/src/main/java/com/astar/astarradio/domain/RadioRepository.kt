package com.astar.astarradio.domain

import com.astar.astarradio.core.Result

interface RadioRepository {

    suspend fun fetchRadioStations(): Result<List<RadioDomain>, Throwable>

    suspend fun fetchRadioStationById(id: Int): Result<RadioDomain, Throwable>

    suspend fun fetchFavoriteRadioStations(): Result<List<RadioDomain>, Throwable>

    suspend fun addToFavorite(radio: RadioDomain)
}