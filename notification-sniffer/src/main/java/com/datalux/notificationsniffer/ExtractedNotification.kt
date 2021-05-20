package com.datalux.notificationsniffer

data class ExtractedNotification(
    var title: String,
    var text: String,
    var subText: String,
    var app: String,
    var timestamp: Long
)