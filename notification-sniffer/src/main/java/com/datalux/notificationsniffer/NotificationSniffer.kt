package com.datalux.notificationsniffer

import android.util.Log
import android.widget.TextView
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext
import kotlin.math.log


class NotificationSniffer {

    companion object {

        private var ot: OutputType = OutputType.STREAM
        private val stream = Channel<ExtractedNotification>()
        private val buffer = ArrayList<ExtractedNotification>()

        lateinit var callback: SniffCallback

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

        fun startSniffing(){
            val periodicWorkRequest =
                PeriodicWorkRequestBuilder<SnifferService>(24, TimeUnit.HOURS).build()
            WorkManager.getInstance().enqueue(periodicWorkRequest)
        }

        fun getNotifications(): ArrayList<ExtractedNotification> {
            return buffer;
        }

        fun setOutputType(outputType: OutputType){
            ot = outputType
        }

    }

}

enum class OutputType {
    STREAM, BUFFER
}