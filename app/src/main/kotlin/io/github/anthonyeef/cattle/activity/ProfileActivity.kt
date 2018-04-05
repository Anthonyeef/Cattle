package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.ProfileFragment
import io.github.anthonyeef.cattle.presenter.ProfilePresenter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.withArguments
import org.jetbrains.anko.verticalLayout

/**
 * host activity for [ProfileFragment]
 */
class ProfileActivity : BaseActivity(), AnkoLogger {

  companion object {
    const val EXTRA_USER_ID = "extra_user_id"
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    verticalLayout {
      id = CONTAINER_ID
      lparams(width = matchParent, height = matchParent)
    }

    if (intent.data != null) {
      intent.putExtra(EXTRA_USER_ID, intent.data.pathSegments[0])
    }

    setupProfileFragment()
  }


  private fun setupProfileFragment() {
    val userInfoId = intent.getStringExtra(EXTRA_USER_ID)
    userInfoId?.let {
      val profileFragment = ProfileFragment().withArguments(ProfileFragment.KEY_USER_ID to it)
      bindFragment(profileFragment)

      ProfilePresenter(profileFragment, it)
    }
  }
}