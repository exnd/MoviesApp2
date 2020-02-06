package com.example.moviesapp2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class Broadcast extends BroadcastReceiver {

    private static int NOTIF_ID_REPEATING = 200;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = "Mau Nonton Film Apa Hari Ini ?";
        String title = "Movies App";


        sendNotification(context, title, message, NOTIF_ID_REPEATING);



    }

    public void setRepeatingAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        Toast.makeText(context, "Daily Reminder Set Up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Broadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private static PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, Broadcast.class);
        return PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void sendNotification(Context context , String judul , String pesan, int notifId){

        Intent intent = new Intent(context ,MainActivity.class);
        intent.putExtra("contohnama","makan1");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 200, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle(judul)
                .setContentText(pesan)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSubText("Daily Reminder")
                .setAutoCancel(true);


        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_NAME.toString());
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(notifId, notification);
        }
    }
}
