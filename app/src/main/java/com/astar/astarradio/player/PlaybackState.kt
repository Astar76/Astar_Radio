package com.astar.astarradio.player

import android.util.Log


class PlaybackState private constructor() {

    private val callbacks = mutableListOf<Callback>()

    private var mRadio: PlayableRadio? = null
        set(value) {
            field = value
            callbacks.forEach { it.onRadioUpdate(value) }
        }

    private var mIsPlaying = false
        set(value) {
            field = value
            callbacks.forEach { it.onPlayingUpdate(value) }
            Log.d(TAG, "mIsPlaying: $value ")
        }

    private var mHasPlayed = false
    private var mIsRestored = false

    val radio: PlayableRadio? get() = mRadio
    val isPlaying: Boolean get() = mIsPlaying
    val isRestored: Boolean get() = mIsRestored
    val hasPlayed: Boolean get() = mHasPlayed

    fun addCallback(callback: Callback) {
        callbacks.add(callback)
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun markRestored() {
        mIsRestored = true
    }

    fun playRadio(radio: PlayableRadio) {
        Log.d(TAG, "playRadio() updating stream to $radio")
        this.mRadio = radio
    }

    fun setPlaying(playing: Boolean) {
        if (mIsPlaying != playing) {
            if (playing) {
                mHasPlayed = true
            }
            mIsPlaying = playing
        }
    }

    fun setHasPlayed(hasPlayed: Boolean) {
        mHasPlayed = hasPlayed
    }

    interface Callback {
        fun onRadioUpdate(radio: PlayableRadio?)
        fun onPlayingUpdate(isPlaying: Boolean)
    }

    companion object {

        const val TAG = "PlaybackStateManager"

        @Volatile
        private var INSTANCE: PlaybackState? = null

        fun newInstance(): PlaybackState {
            val instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                val newInstance = PlaybackState()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}

