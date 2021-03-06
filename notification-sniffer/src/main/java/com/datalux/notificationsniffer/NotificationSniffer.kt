package com.datalux.notificationsniffer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.util.concurrent.TimeUnit


class NotificationSniffer {

    companion object {

        private var ot: OutputType = OutputType.STREAM
        private val stream = Channel<ExtractedNotification>()
        private val buffer = ArrayList<ExtractedNotification>()

        lateinit var listener: SniffListener

        suspend fun send(notification: ExtractedNotification){
            if(ot == OutputType.STREAM)
                streamSend(notification)
            else
                bufferSend(notification)
        }

        private suspend fun streamSend(notification: ExtractedNotification) {
            stream.send(notification)
        }

        private fun bufferSend(notification: ExtractedNotification){
            buffer.add(notification)
        }

        suspend fun consume(consume: (ExtractedNotification) -> Unit) {
            stream.consumeEach { notification -> consume(notification) }
        }

        fun startSniffing(context: Context){
            val periodicWorkRequest =
                PeriodicWorkRequestBuilder<SnifferService>(24, TimeUnit.HOURS).build()
            WorkManager.getInstance(context).enqueue(periodicWorkRequest)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun askForPermission(context: Context){
            context.startActivity( Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        fun checkForPermission(context: Context): Boolean{
            val cn = ComponentName(context, Sniffer::class.java)
            val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
            return flat != null && flat.contains(cn.flattenToString())
        }

        @Suppress("unused")
        fun getNotifications(): ArrayList<ExtractedNotification> {
            return buffer
        }

        @Suppress("unused")
        fun setOutputType(outputType: OutputType){
            ot = outputType
        }

    }

}

@Suppress("unused")
enum class OutputType {
    STREAM, BUFFER
}