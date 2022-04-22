package com.astar.astarradio.data.remote

import com.google.gson.annotations.SerializedName

data class RadioStationsCloud(
    @SerializedName("radios") val list: List<RadioStationCloud>
)

data class SimpleRadioStationCloud(
    @SerializedName("radio") val radio: RadioStationCloud
)

data class RadioStationCloud(
    @SerializedName("image_url")
    val preview: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("uri")
    val streamingUrl: String,
    @SerializedName("channel_id")
    val channelId: Int,
    @SerializedName("countryCode")
    val countryCode: String?,
    @SerializedName("genre")
    val genre: String
)