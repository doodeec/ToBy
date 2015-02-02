package com.doodeec.toby.appstate;

import android.app.Application;
import android.content.Context;

/**
 * Holds state of the application
 * @author Dusan Bartos
 */
public class AppState extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
