package com.example.yogi.gossipsbolo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class ConnectionUtils {
    static public int CONNECT_TYPE_3G = 0;
    static public int CONNECT_TYPE_WIFI = 1;

    /**
     * Check network connection is available or not
     *
     * @return boolean value
     */
    public static boolean isOnline(Context context) {
        Log.d("check_gblogin","ConnectionUtils.isonline() called");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Log.d("check_gblogin","ConnectionUtils.isonline() called and returning.......");
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

}
