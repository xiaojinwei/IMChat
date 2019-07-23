package com.cj.chat;

import android.app.Application;

/**
 * @author cj
 * @description: application
 * @date :2019/6/7 22:24
 */
public class ChatApp extends Application {

    private static ChatApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ChatApp getInstance(){
        if (instance == null) {
            throw new IllegalStateException("application not init...");
        }
        return instance;
    }
}
