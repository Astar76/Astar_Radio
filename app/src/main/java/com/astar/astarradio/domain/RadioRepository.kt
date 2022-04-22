package com.astar.astarradio.domain

import com.astar.astarradio.core.Result

interface RadioRepository {

    suspend fun fetchRadioStations(): Result<List<RadioStation>, Throwable>

    suspend fun fetchRadioStationById(id: Int): Result<RadioStation, Throwable>
}