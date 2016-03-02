package com.lowerback.lowerback.Receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.lowerback.lowerback.R;
import com.lowerback.lowerback.Services.SchedulingService;


public class AlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, SchedulingService.class);
        startWakefulService(context, service);

    }

    public void setAlarm(Context context) {


        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

         // Wake up the device to fire the alarm in 30 minutes, and every 30 minutes
         // after that.
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.alarm_interval_key_pref), context.MODE_PRIVATE);
        String alarmIntervalPref  = sharedPref.getString(context.getString(R.string.alarm_interval_alarm_interval_pref)
                , context.getString(R.string.alarm_interval_default));

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + Long.parseLong(alarmIntervalPref) * 60 * 1000,
                Long.parseLong(alarmIntervalPref) * 60 * 1000, alarmIntent);

    }

    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context,
                    1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.cancel();



    }

}
