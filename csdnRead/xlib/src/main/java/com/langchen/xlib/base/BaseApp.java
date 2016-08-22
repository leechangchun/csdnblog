package com.langchen.xlib.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2016/8/22.
 */
public class BaseApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
