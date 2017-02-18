package com.example.yogi.gossipsbolo.FireBase.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.os.Build;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.NotificationCompat;

import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.yogi.gossipsbolo.R;
import com.example.yogi.gossipsbolo.constant.FireBaseConstants;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Yogi on 28-01-2017.
 */

public class NotificationUtils {

    private String TAG = NotificationUtils.class.getSimpleName();
    private Context context;

    public NotificationUtils(Context context)
    {
        this.context = context;
    }

    public void ShowNotificationMessage(String title, String message, String timestamp, Intent intent)

    {
           ShowNotificationMessage(title, message, timestamp, intent,null);
    }

    public void ShowNotificationMessage(String title,String message,String timestamp,Intent intent,String ImageUrl)
    {
        //check for empty push message
        if(TextUtils.isEmpty(message))
        {
            return;

        }
         final int icon = R.mipmap.ic_launcher;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);


        final Uri alarmSound =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/ping");

        if(!TextUtils.isEmpty(ImageUrl))
        {
             if(ImageUrl != null && ImageUrl.length() > 4 && Patterns.WEB_URL.matcher(ImageUrl).matches())
             {
                 Bitmap bitmap = getBitMapFromUrl(ImageUrl);
                 if(bitmap != null)
                 {
                     showBigNotification(bitmap,mBuilder,icon,title,message,timestamp,resultPendingIntent,alarmSound);
                 }
                 else
                 {
                     showSmallNotification(mBuilder,icon,title,message,timestamp,resultPendingIntent,alarmSound);
                     playNotificationSound();
                 }
             }
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timestamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timestamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),icon))
                .setContentText(message)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(FireBaseConstants.NOTIFICATION_ID,notification);


    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timestamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMilliSec(timestamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),icon))
                .setContentText(message)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(FireBaseConstants.NOTIFICATION_ID_BIG_IMAGE,notification);


    }

    private long getTimeMilliSec(String timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try
        {
            Date date = format.parse(timestamp);
            return date.getTime();

        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }





    private Bitmap getBitMapFromUrl(String imageUrl) {
        try
        {
            URL url= new URL(imageUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.connect();

            InputStream input= httpUrlConnection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //check if the app is in background
    public static  boolean isAppIsInBackground(Context context)
    {
     boolean isInBackground = true;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH)
                {
                    List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
                    for(ActivityManager.RunningAppProcessInfo processInfo : runningProcesses)
                    {
                        if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                        {
                            for(String activeProcess : processInfo.pkgList)
                            {
                                if(activeProcess.equals(context.getPackageName()))
                                    isInBackground = false;
                            }
                        }
                    }
                }
        else
                {
                    List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
                    ComponentName componentInfo = taskInfo.get(0).topActivity;

                    if(componentInfo.getPackageName().equals(context.getPackageName()))
                    {
                        isInBackground = false;
                    }

                }
return isInBackground;

    }

    //clear Notification tray
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/ping");
            Ringtone ringTone = RingtoneManager.getRingtone(context, alarmSound);
            ringTone.play();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
