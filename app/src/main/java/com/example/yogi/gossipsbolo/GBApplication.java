package com.example.yogi.gossipsbolo;


import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GBApplication extends Application{

    private static GBApplication mInstance;
    private RequestQueue mRequestQueue;


    //onCreate will call only one time
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized GBApplication getInstance() {
        Log.d("check_gblogin","GBApplication getInstance of called");
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
}
