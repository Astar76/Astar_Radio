package com.astar.astarradio.di

import android.content.Context
import com.astar.astarradio.data.local.AppDatabase
import com.astar.astarradio.data.local.RadioStationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.newInstance(context)

    @Provides
    fun provideRadioStationsDao(database: AppDatabase): RadioStationsDao =
        database.radioStationDao()
}