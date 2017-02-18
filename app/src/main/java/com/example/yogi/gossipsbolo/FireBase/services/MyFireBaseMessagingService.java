package com.example.yogi.gossipsbolo.FireBase.services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.yogi.gossipsbolo.ChatRoom.activity.ChatRoomActivity;
import com.example.yogi.gossipsbolo.FireBase.utils.NotificationUtils;
import com.example.yogi.gossipsbolo.constant.FireBaseConstants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yogi on 26-01-2017.
 */

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG=MyFireBaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("message", "From" + remoteMessage.getFrom());

        if(remoteMessage == null)
        {
            return;
        }

        if(remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "NotificationMessage" + remoteMessage.getNotification().getBody());
            handelNotification(remoteMessage.getNotification().getBody());
        }
        if(remoteMessage.getData().size() > 0)
        {
          Log.d(TAG,"Data Payload:" + remoteMessage.getData().toString());
            try
            {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.e(TAG , "Exception:" + e.getMessage());
            }
        }



    }

    private void handelNotification(String body) {
        if(!NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            //app is in foreground broadcast the message
            Intent pushNotification = new Intent(FireBaseConstants.PUSH_NOTIFIACTION);
            pushNotification.putExtra("message",body);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            //playNotification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
        else
        {
            //the firebase itself handles the notification
        }

    }
    private void handleDataMessage(JSONObject json) {
        Log.d(TAG,"Push json" + json.toString());

        try{
        JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(FireBaseConstants.PUSH_NOTIFIACTION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent, String imageUrl) {
        notificationUtils = new NotificationUtils(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.ShowNotificationMessage(title, message, timestamp, resultIntent, imageUrl);
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent)
    {
        notificationUtils = new NotificationUtils(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.ShowNotificationMessage(title, message, timestamp, resultIntent);
    }


}
