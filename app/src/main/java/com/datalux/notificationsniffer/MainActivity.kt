package com.datalux.notificationsniffer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent().apply {
            action = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        if(!hasPermission(this))
            startActivity(intent)

        NotificationSniffer.startSniffing()

        NotificationSniffer.callback = object : SniffCallback {
            override fun onResult(extractedNotification: ExtractedNotification) {
                Log.d("onMain", extractedNotification.toString())

            }
        }
    }


    private fun hasPermission(ctx: Context): Boolean {
        val cn = ComponentName(ctx, Sniffer::class.java)
        val flat = Settings.Secure.getString(ctx.contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(cn.flattenToString())
    }
}


