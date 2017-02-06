package io.github.anthonyeef.cattle;

import android.app.Application;
import android.util.Log;

/**
 *
 */
public class App extends Application {
    private static final String TAG = "Application";

    private static App sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App get() {
        if (sInstance == null) {
            Log.e(TAG, "App instance is null");
        }
        return sInstance;
    }
}
