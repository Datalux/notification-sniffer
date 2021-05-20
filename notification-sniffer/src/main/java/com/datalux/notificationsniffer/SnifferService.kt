package com.datalux.notificationsniffer

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.datalux.notificationsniffer.NotificationSniffer.Companion.callback
import kotlinx.coroutines.runBlocking

class SnifferService(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        runBlocking {
            NotificationSniffer.consume { extractedNotificationResult ->
                callback.onResult(extractedNotificationResult)
            }
        }
        return Result.success()
    }
}
