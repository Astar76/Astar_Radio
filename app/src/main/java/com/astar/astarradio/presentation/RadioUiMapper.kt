package com.astar.astarradio.presentation

import com.astar.astarradio.domain.RadioDomain
import javax.inject.Inject

interface RadioUiMapper {

    fun map(radio: RadioDomain): RadioUi

    fun map(radio: RadioUi): RadioDomain

    class Base @Inject constructor(): RadioUiMapper {
        override fun map(radio: RadioDomain): RadioUi {
            return RadioUi(
                name = radio.name,
                streamingUrl = radio.streamingUrl,
                previewUrl = radio.preview,
                countryCode = radio.countryCode,
                genre = radio.genre,
                isFavorite = radio.isFavorite
            )
        }

        override fun map(radio: RadioUi): RadioDomain {
            return RadioDomain(
                name = radio.name,
                streamingUrl = radio.streamingUrl,
                preview = radio.previewUrl,
                countryCode = radio.countryCode,
                genre = radio.genre,
                isFavorite = radio.isFavorite
            )
        }
    }
}
