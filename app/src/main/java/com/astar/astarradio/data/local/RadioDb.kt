package com.astar.astarradio.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "radio_stations")
data class RadioDb(
    val name: String,
    @ColumnInfo(name = "stream_url")
    val streamUrl: String,
    @ColumnInfo(name = "country_code")
    val countryCode: String,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    val genre: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true)
    var radioId: Long = 0
}