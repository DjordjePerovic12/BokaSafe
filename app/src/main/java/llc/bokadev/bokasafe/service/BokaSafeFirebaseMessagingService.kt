package llc.bokadev.bokasafe.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import llc.bokadev.bokasafe.MainActivity
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.domain.repository.BokaSafeRepository
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BokaSafeFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: BokaSafeRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        Timber.e("TOKEN $token")
        super.onNewToken(token)
        GlobalScope.launch {
            repository.saveFcmToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.e("TOKEN NOTG ${message.data}")
        val notificationTitle = message.data["title"]
        val notificationBody = message.data["body"]
        sendNotification(notificationTitle, notificationBody)
    }

    private fun sendNotification(
        messageTitle: String?,
        messageBody: String?,
//        messageClickAction: String?,
    ) {
        Timber.d("Entered sendNotification, title: $messageTitle, body: $messageBody")

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, mainIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setColorized(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Bokasafe notification channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(
            createNotificationId(),
            notificationBuilder.build()
        )

    }

    fun createNotificationId(): Int {
        val now = Date()
        return Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.UK).format(now))
    }


}