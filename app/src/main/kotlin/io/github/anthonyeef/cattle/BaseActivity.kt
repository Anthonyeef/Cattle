package io.github.anthonyeef.cattle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class BaseActivity : AppCompatActivity() {
    val TAG = "CattleBaseActivity"
    lateinit var btn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            btn = textView("hello world")
            initBtn()
        }
    }

    private fun initBtn() {
        btn.onClick {

        }
    }
}
