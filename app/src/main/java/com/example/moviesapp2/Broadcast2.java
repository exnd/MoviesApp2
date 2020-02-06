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
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Broadcast2 extends BroadcastReceiver {

    private static int NOTIF_ID_REPEATING = 100;
    public static String CHANNEL_ID = "channel_02";
    public static CharSequence CHANNEL_NAME = "dicoding channel2";


    @Override
    public void onReceive(final Context context, Intent intent) {


        final ArrayList<Movie> moviehari = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);
        AsyncHttpClient client = new AsyncHttpClient();


        String url = "";
        if (Locale.getDefault().getLanguage().equals("in")){
            url = "https://api.themoviedb.org/3/discover/movie?api_key=cedfdb517def7da8a93ba61da369b966&primary_release_date.gte="+today+"&primary_release_date.lte="+today;


        }else{
            url = "https://api.themoviedb.org/3/discover/movie?api_key=cedfdb517def7da8a93ba61da369b966&primary_release_date.gte="+today+"&primary_release_date.lte="+today;
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray hasilsize = responseObject.getJSONArray("results");

                    for (int i = 0; i < hasilsize.length(); i++) {
                        JSONObject hasil = responseObject.getJSONArray("results").getJSONObject(i);

                        Movie movie = new Movie();
                        String nama2 = hasil.getString("title");
                        String photo = hasil.getString("poster_path");
                        String photo2 = "https://image.tmdb.org/t/p/w500" + photo;
                        String desc2 = hasil.getString("overview");
                        Log.d("_DATA 99",nama2);
                        movie.setName(nama2);
                        movie.setDescription(desc2);
                        movie.setPhoto(photo2);
                        moviehari.add(movie);
                        Log.d("_UKURAN",String.valueOf(moviehari.size()));


                        Log.d("_MVDATA",moviehari.get(i).getName());


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY , 8);

                        String title = movie.getName();
                        String message = "Film "+title+" Telah Rilis";
                        sendNotification(context, title, message, NOTIF_ID_REPEATING);

                        NOTIF_ID_REPEATING++;


                    }


                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });



    }



    public void setRepeatingAlarm(final Context context) {


        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));

        Toast.makeText(context, "New Movie Reminder Set Up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Broadcast2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private static PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, Broadcast2.class);

        return PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void sendNotification(Context context , String judul , String pesan, int notifId){

        Intent intent = new Intent(context ,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle(judul)
                .setContentText(pesan)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSubText("New Movie Reminder")
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
