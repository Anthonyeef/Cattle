package io.github.anthonyeef.cattle;

import android.app.Application;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

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
        sInstance = this;

        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true)
                .build());
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
