package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.TestFieldFragment

/**
 *
 */
class TestFieldActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "TestField Activity"

        // fixme
        /*verticalLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
        }*/

        bindFragment(TestFieldFragment())
    }
}