package com.example.yogi.gossipsbolo.util;

import android.util.Log;

import com.example.yogi.gossipsbolo.Config;

/**
 * Created by Yogi on 12-12-2016.
 */
public class GBLoggerUtil{
    private static GBLoggerUtil instance;
    public static boolean mLogEnabled = Config.DEBUG;

    public static GBLoggerUtil getInstance() {
        if (instance == null) {
            synchronized (GBLoggerUtil.class) {
                if (instance == null) {
                    instance = new GBLoggerUtil();
                }
            }
        }
        return instance;
    }

    public static void debug(Object tag,String msg)
    {
        if(mLogEnabled)
        {
            Log.d(tag.toString(),msg);
        }
    }
    public static void error(Object tag,String msg)
    {
        if(mLogEnabled)
        {
            Log.e(tag.toString(),msg);
        }
    }

}
