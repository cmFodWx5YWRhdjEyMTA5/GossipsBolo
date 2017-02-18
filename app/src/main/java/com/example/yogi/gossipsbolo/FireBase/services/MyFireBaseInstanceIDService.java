package com.example.yogi.gossipsbolo.FireBase.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.yogi.gossipsbolo.constant.FireBaseConstants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Yogi on 27-01-2017.
 */

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService{

    public static final String TAG = MyFireBaseInstanceIDService.class.getSimpleName();





    @Override
    public void onTokenRefresh() {
            super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("refreshedToken", " " + refreshedToken);

        storeRedIdInSharedPref(refreshedToken);

        //sendRegistrationToServer(refreshedToken);
        // Notify UI that registration has completed, so the progress indicator can be hidden.

        Intent registrationComplete = new Intent(FireBaseConstants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token",refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);


    }

    private void storeRedIdInSharedPref(String refreshedToken) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FireBaseConstants.SHARED_PREF,0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("regId",refreshedToken);

        editor.commit();
        Log.e(TAG,"FireBase Reistration ID:" + editor);
    }

//    private void sendRegistrationToServer(String refreshedToken) {
    //Log.e(TAG , "SendRegistrationServer " + refreshedToken );
//    }
}
