package com.o7services.androidkotlin_9_11am

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar

class FireBaseServices:FirebaseMessagingService(){
    private val TAG = "FireBaseServices"
    var firebase = ""
    val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var notificationData= message.notification
        print("notification title ${notificationData?.title}")
        print("notification body ${notificationData?.body}")
        println("type ${notificationData}")
        print("notification data ${message.data}")
        Log.e(TAG, "onMessageReceived: ${notificationData?.title} ${message.data}", )
        firebase = message.data.toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelId = getString(R.string.default_notification_channel_id)

            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        generateNotification(message)

    }

    private fun generateNotification(notificationData: RemoteMessage, bitmap: Bitmap?= null) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("data", firebase)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP )
        var pendingIntent = PendingIntent.getActivity(this, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(this, resources.getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationData?.notification?.title?:"")
            .setContentText(notificationData?.notification?.body?:"")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)

        if(bitmap != null){
            builder.setLargeIcon(bitmap)
        }

        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())

    }
}
