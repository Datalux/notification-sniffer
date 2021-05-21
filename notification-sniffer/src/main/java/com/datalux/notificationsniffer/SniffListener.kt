package com.datalux.notificationsniffer

interface SniffListener {
    fun onResult(extractedNotification: ExtractedNotification)
}
