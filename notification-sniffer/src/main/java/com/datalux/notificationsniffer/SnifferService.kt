package com.datalux.notificationsniffer

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.datalux.notificationsniffer.NotificationSniffer.Companion.listener
import kotlinx.coroutines.runBlocking

internal class SnifferService(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        runBlocking {
            NotificationSniffer.consume { extractedNotificationResult ->
                listener.onResult(extractedNotificationResult)
            }
        }
        return Result.success()
    }
}
