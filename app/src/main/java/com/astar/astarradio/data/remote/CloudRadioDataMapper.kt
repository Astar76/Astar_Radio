package com.astar.astarradio.data.remote

import com.astar.astarradio.data.RadioData
import javax.inject.Inject

interface CloudRadioDataMapper {

    fun map(radio: RadioCloud): RadioData

    fun map(radio: SimpleRadioStationCloud): RadioData

    class Base @Inject constructor() : CloudRadioDataMapper {
        override fun map(radio: RadioCloud): RadioData {
            return RadioData(
                name = radio.name,
                streamingUrl = radio.streamingUrl,
                countryCode = radio.countryCode ?: "unknown",
                previewUrl = radio.preview,
                genre = radio.genre,
                isFavorite = false
            )
        }

        override fun map(radio: SimpleRadioStationCloud): RadioData {
            return RadioData(
                name = radio.radio.name,
                streamingUrl = radio.radio.streamingUrl,
                countryCode = radio.radio.countryCode ?: "unknown",
                previewUrl = radio.radio.preview,
                genre = radio.radio.genre,
                isFavorite = false
            )
        }
    }
}