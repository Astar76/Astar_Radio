package com.astar.astarradio.data

data class RadioData(
    val name: String,
    val streamingUrl: String,
    val countryCode: String,
    val previewUrl: String,
    val genre: String,
    val isFavorite: Boolean,
)