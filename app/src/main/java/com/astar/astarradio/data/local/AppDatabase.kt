package com.astar.astarradio.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RadioDb::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun radioStationDao(): RadioStationsDao

    companion object {

        private const val NAME_DATABASE = "app_database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun newInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, NAME_DATABASE)
                .fallbackToDestructiveMigration()
                .build()

    }
}