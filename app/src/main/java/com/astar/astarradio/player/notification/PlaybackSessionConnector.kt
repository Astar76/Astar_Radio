package com.astar.astarradio.player.notification

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.astar.astarradio.player.PlayableRadio
import com.astar.astarradio.player.PlaybackState
import com.astar.astarradio.player.PlayerService
import com.google.android.exoplayer2.Player

class PlaybackSessionConnector(
    private val context: Context,
    private val player: Player,
    private val mediaSession: MediaSessionCompat
) : PlaybackState.Callback, Player.Listener, MediaSessionCompat.Callback() {
    private val playbackState = PlaybackState.newInstance()
    private val metadata = MediaMetadataCompat.Builder().build()

    init {
        mediaSession.setCallback(this)
        playbackState.addCallback(this)
        player.addListener(this)

        onRadioUpdate(playbackState.radio)
        onPlayingUpdate(playbackState.isPlaying)
    }

    fun release() {
        playbackState.removeCallback(this)
        player.removeListener(this)
    }

    override fun onPlay() {
        playbackState.setPlaying(true)
    }

    override fun onPause() {
        playbackState.setPlaying(false)
    }

    override fun onStop() {
        context.sendBroadcast(Intent(PlayerService.ACTION_EXIT))
    }

    override fun onRadioUpdate(radio: PlayableRadio?) {
        if (radio == null) {
            mediaSession.setMetadata(metadata)
            return
        }

        val builder = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, radio.name)

        mediaSession.setMetadata(builder.build())
    }

    override fun onPlayingUpdate(isPlaying: Boolean) {
        invalidateSessionState()
    }

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.containsAny(Player.EVENT_IS_PLAYING_CHANGED, Player.EVENT_PLAY_WHEN_READY_CHANGED, Player.EVENT_PLAYBACK_PARAMETERS_CHANGED)) {
            invalidateSessionState()
        }
    }

    private fun invalidateSessionState() {
        val state = PlaybackStateCompat.Builder()
            .setActions(ACTIONS)
        mediaSession.setPlaybackState(state.build())
    }

    companion object {
        const val ACTIONS = PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_STOP
    }
}