package com.astar.astarradio.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.astar.astarradio.data.RadioData

@Dao
interface RadioStationsDao {

    @Query("SELECT * FROM radio_stations")
    suspend fun fetchAll(): List<RadioDb>

    @Query("SELECT * FROM radio_stations WHERE is_favorite = '1'")
    suspend fun fetchFavorites(): List<RadioDb>

    @Insert
    suspend fun insertAll(radioStations: List<RadioDb>)

    @Query("DELETE FROM radio_stations")
    suspend fun deleteAll()

    @Query("UPDATE radio_stations SET is_favorite = :favorite WHERE name = :name")
    suspend fun addToFavorite(name: String, favorite: Boolean)
}