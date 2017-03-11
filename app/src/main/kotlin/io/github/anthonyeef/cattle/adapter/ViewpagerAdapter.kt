package io.github.anthonyeef.cattle.adapter

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 *
 */
class ViewpagerAdapter : FragmentPagerAdapter {
    constructor(fm: FragmentManager) : super(fm)

    private val fragmentsList: MutableList<Fragment> = ArrayList()
    private val fragmentListTitles: MutableList<Int> = ArrayList()

    fun addFragment(fragment: Fragment, @StringRes title: Int) {
        fragmentsList.add(fragment)
        fragmentListTitles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return fragmentListTitles[position]
        return null
    }

    fun getTitleForToolbar(position: Int): Int {
        return fragmentListTitles[position]
    }
}