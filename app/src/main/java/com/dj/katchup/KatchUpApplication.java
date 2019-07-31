package com.dj.katchup;

import android.app.Application;
import android.content.Context;

public class KatchUpApplication extends Application {
    private static  KatchUpApplication ourInstance ;

    public static KatchUpApplication getInstance() {
        return ourInstance;
    }

    private Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        context = getApplicationContext();
    }

    public Context getContext() {
        return context;
    }
}
