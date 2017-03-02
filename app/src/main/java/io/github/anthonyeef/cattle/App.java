package io.github.anthonyeef.cattle;

import android.app.Application;
import android.util.Log;

import io.github.anthonyeef.cattle.event.RxBus;
import io.realm.Realm;

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
        sInstance = this;

        Realm.init(this);
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
