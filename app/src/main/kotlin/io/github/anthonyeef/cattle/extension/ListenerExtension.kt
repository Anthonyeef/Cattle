package io.github.anthonyeef.cattle.extension

import android.support.design.widget.TabLayout

class _OnTabSelectedListener : TabLayout.OnTabSelectedListener {
    private var _onTabSelected: ((tab: TabLayout.Tab) -> Unit)? = null
    private var _onTabUnSelected: ((tab: TabLayout.Tab) -> Unit)? = null
    private var _onTabReselected: ((tab: TabLayout.Tab) -> Unit)? = null

    override fun onTabSelected(tab: TabLayout.Tab) {
        _onTabSelected?.invoke(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        _onTabReselected?.invoke(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        _onTabUnSelected?.invoke(tab)
    }

    fun onTabSelected(func: (TabLayout.Tab) -> Unit) {
        _onTabSelected = func
    }

    fun onTabUnSelected(func: (TabLayout.Tab) -> Unit) {
        _onTabUnSelected = func
    }

    fun onTabReSelected(func: (TabLayout.Tab) -> Unit) {
        _onTabReselected = func
    }
}

inline fun TabLayout.addOnTabSelectedListener(func: _OnTabSelectedListener.() -> Unit) {
    val listener = _OnTabSelectedListener()
    listener.func()
    addOnTabSelectedListener(listener)
}