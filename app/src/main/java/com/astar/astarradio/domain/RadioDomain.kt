package com.astar.astarradio.domain

data class RadioDomain(
    val name: String,
    val preview: String,
    val streamingUrl: String,
    val countryCode: String,
    val genre: String,
    val isFavorite: Boolean
)