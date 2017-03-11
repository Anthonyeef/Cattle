package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.anthonyeef.cattle.constant.verticalPaddingLarge
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

/**
 *
 */
class TestFieldFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                topPadding = verticalPaddingLarge

                button("click") {
                    onClick {
                        (it as Button).text = "clicked"
                    }
                }
            }
        }.view
    }
}