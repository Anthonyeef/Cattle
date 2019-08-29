package io.github.anthonyeef.cattle.activity

import android.content.Intent
import android.os.Bundle
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.*
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.fragment.ComposeFragment
import io.github.anthonyeef.cattle.fragment.LoginFragment
import io.github.anthonyeef.cattle.utils.PrefUtils.defaultPref

/**
 *
 */
class LoginActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (defaultPref.getString(KEY_CURRENT_USER_ID, "").isNotEmpty()) {
      startActivity(Intent(app, HomeActivity::class.java))
      finish()

      if (intent?.action == CATTLE_COMPOSE) {
        ComposeFragment().show()
      }
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