package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.extension.TAG
import io.github.anthonyeef.cattle.fragment.LoginFragment
import org.jetbrains.anko.*

class DebugActivity : BaseActivity() {
    val CONTAINER_ID = 110
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Debug activity"

        verticalLayout {
            id = CONTAINER_ID
            horizontalPadding = pageHorizontalPadding
            verticalPadding = verticalPaddingNormal
            button("Go to authorize activity") {
                onClick {
                    val loginFragment: Fragment = LoginFragment()
                    supportFragmentManager.beginTransaction()
                            .addToBackStack(loginFragment.TAG)
                            .replace(CONTAINER_ID, loginFragment).commit()
                }
            }
        }
    }
}
