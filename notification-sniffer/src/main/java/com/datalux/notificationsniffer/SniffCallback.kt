package com.datalux.notificationsniffer

interface SniffCallback {
    fun onResult(extractedNotification: ExtractedNotification)
}
