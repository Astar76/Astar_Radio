package com.astar.astarradio.data

import com.astar.astarradio.data.remote.RadioStationCloud
import com.astar.astarradio.domain.RadioStation
import javax.inject.Inject

interface RadioStationDomainMapper {

    fun map(station: RadioStationCloud): RadioStation

    class Base @Inject constructor() : RadioStationDomainMapper {
        override fun map(station: RadioStationCloud) = RadioStation(
            name = station.name,
            streamingUrl = station.streamingUrl,
            countryCode = station.countryCode ?: "none",
            genre = station.genre
        )
    }
}