package com.astar.astarradio.player.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.*
import com.astar.astarradio.R
import com.astar.astarradio.data.remote.RadioService
import com.astar.astarradio.player.PlayableRadio
import com.astar.astarradio.player.PlayerService
import com.astar.astarradio.presentation.MainActivity

@SuppressLint("RestrictedApi")
class PlayerNotification private constructor(
    private val context: Context,
    mediaToken: MediaSessionCompat.Token,
) : NotificationCompat.Builder(context, CHANNEL_ID) {

    init {
        setSmallIcon(R.drawable.ic_baseline_play_arrow)
        setCategory(NotificationCompat.CATEGORY_SERVICE)
        //setShowWhen(false)
        //setSilent(true)
        setContentIntent(context.targetScreenIntent())
        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        addAction(createPlayOrStopAction(context, true))
        addAction(createExitAction(context))

        setStyle(MediaStyle()
            .setMediaSession(mediaToken)
            .setShowActionsInCompactView(1)
        )
    }

    fun setMetaData(radio: PlayableRadio) {
        setContentTitle(radio.name)
        // setContentText(...)
    }

    fun setPlaying(playing: Boolean) {
        mActions[0] = createPlayOrStopAction(context, playing)
    }

    private fun createExitAction(context: Context) = createAction(
        context,
        PlayerService.ACTION_EXIT,
        R.drawable.ic_baseline_close
    )

    private fun createPlayOrStopAction(context: Context, playing: Boolean) = createAction(
        context,
        PlayerService.ACTION_PLAY_STOP,
        if (playing)
            R.drawable.ic_baseline_stop
        else
            R.drawable.ic_baseline_play_arrow
    )

    private fun createAction(
        context: Context,
        actionName: String,
        @DrawableRes drawableResource: Int,
    ): NotificationCompat.Action {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
        else 0
        val action = PendingIntent.getBroadcast(context, 1090, Intent(actionName), flag)
        return NotificationCompat.Action.Builder(drawableResource, actionName, action).build()
    }

    private fun Context.targetScreenIntent(): PendingIntent {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
        else 0
        return PendingIntent.getActivity(
            this,
            1000,
            Intent(this, MainActivity::class.java),
            flag
        )
    }

    companion object {
        const val CHANNEL_ID = "channel_radio"
        const val NOTIFICATION_ID = 1213

        fun create(
            context: Context,
            notificationManager: NotificationManager,
            mediaSession: MediaSessionCompat,
        ): PlayerNotification {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            return PlayerNotification(context, mediaSession.sessionToken)
        }
    }
}