package com.cj.im.log;

import android.util.Log;

import com.cj.im.BuildConfig;

/**
 * @author cj
 * @description: log
 * @date :2019/6/1 16:08
 */
public class L {
    private static final String TAG = "IM_CHAT_L";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static void d(String s) {
        if (DEBUG) {
            Log.i(TAG,s);
        }
    }

    public static void d(String format, Object... args) {
        if (DEBUG) {
            Log.i(TAG,String.format(format,args));
        }
    }

    public static void d(String tag, String s) {
        if (DEBUG) {
            Log.i(tag,s);
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (DEBUG) {
            Log.i(tag,String.format(format,args));
        }
    }

    public static void e(String s) {
        if (DEBUG) {
            Log.e(TAG,s);
        }
    }

    public static void e(String format, Object... args) {
        if (DEBUG) {
            Log.e(TAG,String.format(format,args));
        }
    }

    public static void e(String tag,String s) {
        if (DEBUG) {
            Log.e(tag,s);
        }
    }

    public static void e(String tag,String format, Object... args) {
        if (DEBUG) {
            Log.e(tag,String.format(format,args));
        }
    }
}
