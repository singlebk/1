package ng.com.kpn.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import ng.com.kpn.MainActivity
import ng.com.kpn.R
import ng.com.kpn.api.KpnApiService
import ng.com.kpn.data.local.SessionManager
import javax.inject.Inject

/**
 * Handles Firebase Cloud Messaging (FCM) Push Notifications.
 * 
 * NOTE TO BACKEND TEAM:
 * To enable real push notifications, the Django backend must implement:
 * 
 * BACKEND_API_REQUIRED: POST /api/v1/devices/register/
 * Request Body: { "fcm_token": "string", "device_type": "android" }
 * Authentication: JWT Bearer (Required)
 */
@AndroidEntryPoint
class KpnFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var apiService: KpnApiService

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Store locally or dispatch to backend if user is authenticated
        // TODO: Launch coroutine in application scope to send to /api/v1/devices/register/
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "KPN Update"
        val message = remoteMessage.notification?.body ?: ""
        
        sendNotification(title, message)
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "kpn_default_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            // .setSmallIcon(R.mipmap.ic_launcher) // TODO: Add transparent push icon
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "KPN Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
