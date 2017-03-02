package io.github.anthonyeef.cattle.activity

import android.content.Intent
import android.os.Bundle
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.constant.bus
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.DebugFragment
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

class DebugActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Debug activity"

        verticalLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
        }

        bindFragment(DebugFragment())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.dataString?.contains("cat") ?: false) {
            if (bus.hasObservers()) {
                bus.send(LoginSuccessEvent())
            }
        }
    }
}