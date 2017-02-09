package io.github.anthonyeef.cattle.activity

import android.support.v7.app.AppCompatActivity
import io.github.anthonyeef.cattle.event.RxBus
import org.jetbrains.anko.AnkoLogger

open class BaseActivity : AppCompatActivity(), AnkoLogger {
    private var _rxBus: RxBus? = null

    fun getRxBusSingleton(): RxBus {
        if (_rxBus == null) {
            _rxBus = RxBus()
        }
        return _rxBus!!
    }
}
