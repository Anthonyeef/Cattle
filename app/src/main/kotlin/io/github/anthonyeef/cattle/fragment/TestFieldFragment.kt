package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.constant.verticalPaddingLarge
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.topPadding
import org.jetbrains.anko.verticalLayout

/**
 *
 */
class TestFieldFragment : BaseFragment() {

    // test id for album
    private val TEST_ALBUM_UID = "waywardgirl" // 绵子
    private val TEST_ALBUM_UID_1 = "luobeibei" // 罗贝贝

    // 401 unauthorized
    private val TEST_ERROR_ID = "~vGIW3w_zPZ4" // 阿丽丽

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                topPadding = verticalPaddingLarge
            }
        }.view
    }
}