package io.github.anthonyeef.cattle;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import io.github.anthonyeef.cattle.event.RxBus;

/**
 *
 */
public class App extends Application {
    private static final String TAG = "Application";

    private static App sInstance = null;
    private static RxBus _rxBus = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());
        sInstance = this;
    }

    public static App get() {
        if (sInstance == null) {
            Log.e(TAG, "App instance is null");
        }
        return sInstance;
    }

    public static RxBus getRxBusSingleton() {
        if (_rxBus == null) {
            _rxBus = new RxBus();
        }
        return _rxBus;
    }
}
