package io.github.anthonyeef.cattle.activity

import android.content.Intent
import android.os.Bundle
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_USER_ID
import io.github.anthonyeef.cattle.constant.bus
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.LoginFragment
import io.github.anthonyeef.cattle.utils.PrefUtils.defaultPref
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 *
 */
class LoginActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (defaultPref.getString(KEY_CURRENT_USER_ID, "").isNotEmpty()) {
      startActivity(intentFor<HomeActivity>())
      finish()
    } else {
      setContentView(R.layout.activity_general)
      bindFragment(LoginFragment())
    }
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    if (intent?.dataString?.contains("cat") == true) {
      if (bus.hasObservers()) {
        bus.send(LoginSuccessEvent())
      }
    }
  }
}