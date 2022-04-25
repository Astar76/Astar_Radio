package com.astar.astarradio.player

import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.content.ContextCompat
import com.astar.astarradio.player.notification.PlaybackSessionConnector
import com.astar.astarradio.player.notification.PlayerNotification
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class PlayerService : Service(), Player.Listener, PlaybackState.Callback {

    lateinit var playbackState: PlaybackState

    private var player: ExoPlayer by Delegates.notNull()
    private var connector: PlaybackSessionConnector by Delegates.notNull()
    private var mediaSession: MediaSessionCompat by Delegates.notNull()
    private var notification: PlayerNotification by Delegates.notNull()
    private var notificationManager: NotificationManager by Delegates.notNull()

    private val receiver = EventReceiver()

    private var isForeground = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        playbackState = PlaybackState.newInstance()

        player = createPlayer()
        player.addListener(this)
        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build(),
            false
        )

        mediaSession = MediaSessionCompat(this, "radio").apply {
            isActive = true
        }

        connector = PlaybackSessionConnector(this, player, mediaSession)

        val intentFilter = IntentFilter().apply {
            addAction(ACTION_PLAY_STOP)
            addAction(ACTION_EXIT)
        }

        registerReceiver(receiver, intentFilter)

        notificationManager = requireNotNull(
            ContextCompat.getSystemService(this, NotificationManager::class.java)
        )
        notification = PlayerNotification.create(this, notificationManager, mediaSession)

        playbackState.setHasPlayed(playbackState.isPlaying)
        playbackState.addCallback(this)

        if (playbackState.radio != null || playbackState.isRestored) {
            restore()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForegroundServiceAndNotification()
        unregisterReceiver(receiver)

        player.release()
        connector.release()
        mediaSession.release()

        playbackState.removeCallback(this)
        playbackState.setPlaying(false)
    }

    private fun createPlayer(): ExoPlayer {
        val audioRenderer = RenderersFactory { handler, _, audioListener, _, _ ->
            arrayOf(MediaCodecAudioRenderer(this,
                MediaCodecSelector.DEFAULT,
                handler,
                audioListener))
        }

        val extractorsFactory = DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true)
        return ExoPlayer.Builder(this, audioRenderer)
            .setMediaSourceFactory(DefaultMediaSourceFactory(this, extractorsFactory))
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()
    }

    private fun restore() {
        onPlayingUpdate(playbackState.isPlaying)
        onRadioUpdate(playbackState.radio)
    }

    private fun startForegroundServiceOrNotify() {
        if (playbackState.hasPlayed && playbackState.radio != null) {
            if (!isForeground) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(
                        PlayerNotification.NOTIFICATION_ID, notification.build(),
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                    )
                } else {
                    startForeground(PlayerNotification.NOTIFICATION_ID, notification.build())
                }
                isForeground = true
            }
        } else {
            notificationManager.notify(PlayerNotification.NOTIFICATION_ID, notification.build())
        }
    }

    private fun stopForegroundServiceAndNotification() {
        stopForeground(true)
        notificationManager.cancel(PlayerNotification.NOTIFICATION_ID)
        isForeground = false
    }

    /** region [PlaybackState.Callback] */

    override fun onRadioUpdate(radio: PlayableRadio?) {
        if (radio != null) {
            player.setMediaItem(MediaItem.fromUri(radio.streamingUrl))
            player.prepare()
            return
        }
        player.stop()
    }

    override fun onPlayingUpdate(isPlaying: Boolean) {
        if (isPlaying && !player.isPlaying) player.play() else player.pause()
        notification.setPlaying(isPlaying)
        startForegroundServiceOrNotify()
    }

    /** endregion [PlaybackState.Callback] */

    private inner class EventReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                ACTION_PLAY_STOP -> {
                    playbackState.setPlaying(!playbackState.isPlaying)
                }
                ACTION_EXIT -> {
                    playbackState.setPlaying(false)
                    stopForegroundServiceAndNotification()
                }
            }
        }
    }

    companion object {
        const val TAG = "PlayerService"

        const val ACTION_EXIT = "action_exit"
        const val ACTION_PLAY_STOP = "action_play_stop"
    }
}