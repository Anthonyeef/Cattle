package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.ComposeFragment
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 * ComposeActivity to host [ComposeFragment]
 */
class ComposeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Compose Activity"

        verticalLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
        }

        bindFragment(ComposeFragment())
    }
}