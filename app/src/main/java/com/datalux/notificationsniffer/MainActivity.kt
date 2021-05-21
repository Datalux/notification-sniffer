package com.datalux.notificationsniffer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!NotificationSniffer.checkForPermission(this))
            NotificationSniffer.askForPermission(this)

        NotificationSniffer.startSniffing()

        NotificationSniffer.listener = object : SniffListener {
            override fun onResult(extractedNotification: ExtractedNotification) {
                Log.d("onMain", extractedNotification.toString())
            }
        }
    }

}


