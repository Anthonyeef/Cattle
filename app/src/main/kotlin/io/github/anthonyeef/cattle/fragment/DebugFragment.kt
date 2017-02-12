package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.HomeActivity
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.contract.DebugContract
import io.github.anthonyeef.cattle.extension.navigateTo
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.presenter.DebugPresenter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast

/**
 * DebugFragment for easy debug or test usage.
 */
class DebugFragment : BaseFragment(), DebugContract.View {
    lateinit var debugPresenter: DebugContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugPresenter(this@DebugFragment)
    }

    override fun setPresenter(presenter: DebugContract.Presenter) {
        debugPresenter = presenter
    }

    override fun goLogin() {
        LoginFragment().show(this)
    }

    override fun goHome() {
        navigateTo<HomeActivity>()
    }

    override fun onError(e: Throwable?) {
        onUiThread {
            toast(e.toString())
        }
        error(e.toString())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                horizontalPadding = pageHorizontalPadding
                topPadding = verticalPaddingNormal + dip(24)
                bottomPadding = verticalPaddingNormal
                button("Go to authorize fragment") {
                    onClick {
                        goLogin()
                    }
                }

                button("Go to HomeActivity") {
                    onClick {
                        goHome()
                    }
                }
            }
        }.view
    }
}