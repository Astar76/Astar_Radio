package com.astar.astarradio.di

import com.astar.astarradio.player.PlaybackState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RadioStateModule {

    @Provides
    @Singleton
    fun providePlaybackState(): PlaybackState = PlaybackState.newInstance()
}