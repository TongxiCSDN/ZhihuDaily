package com.tongxi.zhihudaily.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by qf on 2016/11/8.
 */
public class MyApp extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getApplication() {
        return context;
    }
}
