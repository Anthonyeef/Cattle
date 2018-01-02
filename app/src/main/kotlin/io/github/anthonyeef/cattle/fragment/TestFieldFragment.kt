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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                topPadding = verticalPaddingLarge
            }
        }.view
    }
}