package com.lowerback.lowerback.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.lowerback.lowerback.MainActivity;
import com.lowerback.lowerback.R;
import com.lowerback.lowerback.Receivers.AlarmReceiver;

public class SchedulingService extends IntentService {

    public SchedulingService() {
        super("SchedulingService");

    }


    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;


    @Override
    protected void onHandleIntent(Intent intent) {
       sendNotification(getResources().getString(R.string.notification_title));
        AlarmReceiver.completeWakefulIntent(intent);

    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_interval_key_pref), this.MODE_PRIVATE);
        String alarmMusicPref  = sharedPref.getString(this.getString(R.string.alarm_interval_alarm_interval_pref)
                , this.getString(R.string.alarm_interval_default));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.notification_icon))
                        .setSound(Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.kalimba));
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


}
