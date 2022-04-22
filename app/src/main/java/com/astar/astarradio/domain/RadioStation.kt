package com.astar.astarradio.domain

data class RadioStation(
    val name: String,
    val streamingUrl: String,
    val countryCode: String,
    val genre: String
)