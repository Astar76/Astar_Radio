package com.astar.astarradio.presentation

data class RadioUi(
    val name: String,
    val streamingUrl: String,
    val previewUrl: String,
    val countryCode: String,
    val genre: String,
    var isFavorite: Boolean = false
)