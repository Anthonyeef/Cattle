package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.DebugFragment

class DebugActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Debug activity"

        // fixme
        /*verticalLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
        }*/

        bindFragment(DebugFragment())
    }
}