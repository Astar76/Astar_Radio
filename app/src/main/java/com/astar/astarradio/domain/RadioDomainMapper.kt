package com.astar.astarradio.domain

import com.astar.astarradio.data.RadioData
import javax.inject.Inject

interface RadioDomainMapper {

    fun map(radio: RadioData): RadioDomain

    fun map(radio: RadioDomain): RadioData

    class Base @Inject constructor() : RadioDomainMapper {
        override fun map(radio: RadioData) = RadioDomain(
            name = radio.name,
            preview = radio.previewUrl,
            streamingUrl = radio.streamingUrl,
            countryCode = radio.countryCode,
            genre = radio.genre,
            isFavorite = radio.isFavorite
        )

        override fun map(radio: RadioDomain) = RadioData(
            name = radio.name,
            previewUrl = radio.preview,
            streamingUrl = radio.streamingUrl,
            countryCode = radio.countryCode,
            genre = radio.genre,
            isFavorite = radio.isFavorite
        )
    }
}