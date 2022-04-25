package com.astar.astarradio.di

import com.astar.astarradio.data.BaseRadioRepository
import com.astar.astarradio.domain.RadioDomainMapper
import com.astar.astarradio.data.local.LocalRadioDataMapper
import com.astar.astarradio.data.local.RadioLocalDataSource
import com.astar.astarradio.data.remote.CloudRadioDataMapper
import com.astar.astarradio.data.remote.RadioCloudDataSource
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.player.PlaybackState
import com.astar.astarradio.presentation.RadioUiMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RadioStationsModule {

    @Binds
    fun provideRadioCloudDataSource(base: RadioCloudDataSource.Base): RadioCloudDataSource

    @Binds
    fun provideRadioLocalDataSource(base: RadioLocalDataSource.Base): RadioLocalDataSource

    @Binds
    fun provideRadioRepository(base: BaseRadioRepository): RadioRepository

    @Binds
    fun provideLocalRadioDataMapper(base: LocalRadioDataMapper.Base): LocalRadioDataMapper

    @Binds
    fun provideCloudRadioDataMapper(base: CloudRadioDataMapper.Base): CloudRadioDataMapper

    @Binds
    fun provideRadioDomainMapper(base: RadioDomainMapper.Base): RadioDomainMapper

    @Binds
    fun provideRadioUiMapper(base: RadioUiMapper.Base): RadioUiMapper

}