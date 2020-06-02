package com.alfredoqt.glucoseprediction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            NotificationManager mNotificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent it = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), it, 0);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = ContextCompat.getColor(this, R.color.colorPrimary);
            String CHANNEL_ID = "FCM_channel_01";
            CharSequence name = "Channel fCM";
            int ico_notification = R.drawable.starkicon;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this,CHANNEL_ID)
                            .setSmallIcon(ico_notification)
                            .setContentTitle(title)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(body))
                            .setSound(soundUri)
                            .setColor(color)
                            .setAutoCancel(true)
                            .setContentText(body);

            mBuilder.setContentIntent(contentIntent);
            Notification notification = mBuilder.build();

            mNotificationManager.notify(0, notification);
        }
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {

    }
}
