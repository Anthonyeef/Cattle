package io.github.anthonyeef.cattle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import io.github.anthonyeef.cattle.repo.AccountRepo
import org.jetbrains.anko.*
import org.oauthsimple.model.OAuthToken

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
            var token: OAuthToken?
            doAsync(exceptionHandler = { Log.e(TAG, it.toString())}) {
                token = AccountRepo.getOAuthAccessToken("test", "test")
                uiThread {
                    Log.e(TAG, token?.token)
                }
            }
        }
    }
}
