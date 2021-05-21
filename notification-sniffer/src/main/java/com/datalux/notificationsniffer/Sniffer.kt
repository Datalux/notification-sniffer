package com.datalux.notificationsniffer

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.app.Notification
import android.util.Log
import kotlinx.coroutines.runBlocking


internal class Sniffer : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val extras = sbn?.notification?.extras

        runBlocking {
            NotificationSniffer.send(
                ExtractedNotification(
                extras?.getString(Notification.EXTRA_TITLE).toString(),
                extras?.get(Notification.EXTRA_TEXT).toString(),
                extras?.getString(Notification.EXTRA_SUB_TEXT).toString(),
                sbn?.packageName.toString(),
                sbn?.postTime!!)
            )
        }
    }

}