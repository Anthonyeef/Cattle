package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.AlbumActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.constant.verticalPaddingLarge
import io.github.anthonyeef.cattle.extension.show
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.withArguments

/**
 *
 */
class TestFieldFragment : BaseFragment() {

  // test id for album
  private val TEST_ALBUM_UID = "waywardgirl" // 绵子
  private val TEST_ALBUM_UID_1 = "luobeibei" // 罗贝贝

  // 401 unauthorized
  private val TEST_ERROR_ID = "~vGIW3w_zPZ4" // 阿丽丽

  // test for status context
  private val TEST_CONTEXT_STATUS_ID = "qxwYe75bO6Q" // 合租

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return UI {
      verticalLayout {
        lparams(width = matchParent, height = matchParent)
        topPadding = verticalPaddingLarge

        button("1 测试相册").onClick {
          app.startActivity(app.intentFor<AlbumActivity>(AlbumActivity.EXTRA_USER_ID to TEST_ALBUM_UID).newTask())
        }

        button("2 测试上下文").onClick {
          StatusDetailFragment()
              .withArguments(StatusDetailFragment.KEY_STATUS_ID to TEST_CONTEXT_STATUS_ID)
              .show()
        }
      }
    }.view
  }
}