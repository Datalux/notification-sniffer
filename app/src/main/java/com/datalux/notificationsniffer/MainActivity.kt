package com.datalux.notificationsniffer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!NotificationSniffer.checkForPermission(this))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                NotificationSniffer.askForPermission(this)
            }

        NotificationSniffer.startSniffing(this)

        NotificationSniffer.listener = object : SniffListener {
            override fun onResult(extractedNotification: ExtractedNotification) {
                Log.d("onMain", extractedNotification.toString())
            }
        }
    }
}


