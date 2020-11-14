package com.example.rabieta.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

object OrdenNotifChannelManager {
    const val NEW_ORDEN_CHANNEL_ID = "1"
    private const val NEW_ORDEN_CHANNEL_NAME = "New Orden"
    private const val NEW_ORDEN_CHANNEL_DESCRIPTION =
        "You'll be notified about new games that are added."

    fun createNotificationChannelForOrden(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = NotificationManagerCompat.from(context)

            val channel = NotificationChannel(
                NEW_ORDEN_CHANNEL_ID,
                NEW_ORDEN_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NEW_ORDEN_CHANNEL_DESCRIPTION

            notificationManager.createNotificationChannel(channel)
        }
    }
}