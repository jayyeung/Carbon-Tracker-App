package com.as3.parmjohal.carbontracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.UI.MainActivity;
import com.as3.parmjohal.carbontracker.UI.SelectCarActivity;
import com.as3.parmjohal.carbontracker.UI.SelectTransActivity;
import com.as3.parmjohal.carbontracker.UI.UtilitiesActivity;

import java.util.ArrayList;
import java.util.Calendar;

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

        PendingIntent notification = PendingIntent.getActivities(context,0,
                new Intent[]{new Intent(context, SelectCarActivity.class)},0);//click the alarm to go to MainAcitivity Edit to make it smart

        String[] outputMessages = {"You Entered No Journey's Today. Would you Like to Enter One?"
                , "You Entered no Bills This Month. Would you Like to Enter One?"
                , "You Entered no Electricity Bill This Month. Would you Like to Enter One?"
                ,"You Entered no Natural Gas Bill This Month. Would you Like to Enter One?"
        ,"Did you Have Anymore Journey's Today?"
        };

        String notificationMessage = " ";

        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR) - 2000;
        double numberOfJourneys_today = 0;

        try {
            ArrayList<Double> data = CarbonTrackerModel.getModel().getDayManager().getPieGraphData_Mode(day,month,year,1);
            numberOfJourneys_today = data.size() - 3;
        }
        catch (java.lang.NullPointerException e)
        {
            System.out.println("KNJNDKJNDKJ");
        }

        double numberOfElectricityBills_PastMonth = CarbonTrackerModel.getModel().getDayManager().getPieGraphData_Mode(day,month,year,28).get(0);
        double numberOfGasBills_PastMonth = CarbonTrackerModel.getModel().getDayManager().getPieGraphData_Mode(day,month,year,28).get(1);

        if(numberOfJourneys_today == 0)
        {
            notificationMessage = outputMessages[0];
            notification = PendingIntent.getActivities(context,0,
                    new Intent[]{new Intent(context, SelectTransActivity.class)},0);
        }
        else if(numberOfElectricityBills_PastMonth + numberOfGasBills_PastMonth == 0)
        {
            notificationMessage = outputMessages[1];
            notification = PendingIntent.getActivities(context,0,
                    new Intent[]{new Intent(context, UtilitiesActivity.class)},0);
        }
        else if(numberOfElectricityBills_PastMonth == 0)
        {
            notificationMessage = outputMessages[2];
            notification = PendingIntent.getActivities(context,0,
                    new Intent[]{new Intent(context, UtilitiesActivity.class)},0);

        }
        else if(numberOfGasBills_PastMonth == 0)
        {
            notificationMessage = outputMessages[3];
            notification = PendingIntent.getActivities(context,0,
                    new Intent[]{new Intent(context, UtilitiesActivity.class)},0);
        }
        else {
            notificationMessage = outputMessages[4] + " You Entered " + numberOfJourneys_today + " Journey's Today";
            notification = PendingIntent.getActivities(context,0,
                    new Intent[]{new Intent(context, SelectTransActivity.class)},0);
        }

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.alarm)
                        .setContentTitle("CarbonTracker")
                        .setTicker("here you go")
                        .setContentText(notificationMessage)
                        .setContentIntent(notification)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setAutoCancel(true);

        NotificationManager mnotificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mnotificationManager.notify(1,mBuilder.build());
    }
}
