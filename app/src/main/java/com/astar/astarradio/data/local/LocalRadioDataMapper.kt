package com.astar.astarradio.data.local

import com.astar.astarradio.data.RadioData
import javax.inject.Inject

interface LocalRadioDataMapper {

    fun map(radio: RadioDb): RadioData

    fun map(radio: RadioData): RadioDb

    class Base @Inject constructor() : LocalRadioDataMapper {
        override fun map(radio: RadioDb): RadioData {
            return RadioData(
                name = radio.name,
                streamingUrl = radio.streamUrl,
                previewUrl = radio.previewUrl,
                countryCode = radio.countryCode,
                genre = radio.genre,
                isFavorite = radio.isFavorite
            )
        }

        override fun map(radio: RadioData): RadioDb {
            return RadioDb(
                name = radio.name,
                streamUrl = radio.streamingUrl,
                countryCode = radio.countryCode,
                previewUrl = radio.previewUrl,
                genre = radio.genre
            )
        }
    }
}