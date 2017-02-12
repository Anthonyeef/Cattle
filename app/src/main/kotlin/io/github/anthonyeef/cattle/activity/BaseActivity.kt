package io.github.anthonyeef.cattle.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.anthonyeef.cattle.constant.TOOLBAR_ID
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.findOptional

open class BaseActivity : AppCompatActivity(), AnkoLogger {

    protected fun setTitle(title: String) {
        val toolBar = findOptional<Toolbar>(TOOLBAR_ID)
        toolBar?.title = title
    }
}
