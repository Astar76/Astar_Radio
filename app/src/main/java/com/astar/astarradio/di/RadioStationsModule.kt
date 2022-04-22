package com.astar.astarradio.di

import com.astar.astarradio.data.BaseRadioRepository
import com.astar.astarradio.data.RadioStationDomainMapper
import com.astar.astarradio.data.remote.RadioCloudDataSource
import com.astar.astarradio.domain.RadioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RadioStationsModule {

    @Binds
    fun provideRadioCloudDataSource(base: RadioCloudDataSource.Base): RadioCloudDataSource

    @Binds
    fun provideRadioRepository(base: BaseRadioRepository): RadioRepository

    @Binds
    fun provideRadioDomainMapper(base: RadioStationDomainMapper.Base): RadioStationDomainMapper
}