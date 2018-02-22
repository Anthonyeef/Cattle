package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.DebugSettingFragment
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.matchParent

/**
 *
 */
class DebugSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coordinatorLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true
        }


        bindFragment(DebugSettingFragment())
    }
}