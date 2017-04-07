package com.as3.parmjohal.carbontracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.UI.MainActivity;
import com.as3.parmjohal.carbontracker.UI.SelectCarActivity;

/**
 * Created by wu on 2017-04-06.
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"here is the alarm",Toast.LENGTH_LONG).show();

        creatNotification(context);

    }

    private void creatNotification(Context context) {

        PendingIntent notification = PendingIntent .getActivities(context,0,
                new Intent[]{new Intent(context, SelectCarActivity.class)},0);//click the alarm to go to MainAcitivity Edit to make it smart

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.alarm)
                        .setContentTitle("My notification")
                        .setTicker("here you go")
                        .setContentText("Hello World!")
                        .setContentIntent(notification)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setAutoCancel(true);

        NotificationManager mnotificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mnotificationManager.notify(1,mBuilder.build());
    }
}
