package com.doodeec.toby.appstate;

import android.app.Application;
import android.content.Context;

/**
 * Holds state of the application
 * @author Dusan Bartos
 */
public class AppState extends Application {

    private static Context applicationContext;
    
    private static boolean syncEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }
    
    public static void setSyncEnabled(boolean enabled) {
        syncEnabled = enabled;
    }
    
    public static boolean getSyncEnabled() {
        return syncEnabled;
    }
}
