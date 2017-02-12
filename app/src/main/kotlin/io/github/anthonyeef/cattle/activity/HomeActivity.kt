package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_USER_ID
import io.github.anthonyeef.cattle.extension.realm.query
import io.github.anthonyeef.cattle.service.model.UserInfo
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.uiThread

/**
 * HomeActivity.
 */
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setTitle("Cattle")

        setupProfileInDrawer()
    }

    private fun setupProfileInDrawer() {
        val navHeader = findOptional<NavigationView>(R.id.nav_view)?.getHeaderView(0)
        val avatar = navHeader?.findOptional<CircleImageView>(R.id.nav_avatar)
        val userName = navHeader?.findOptional<TextView>(R.id.nav_user_name)
        val navBg = navHeader?.findOptional<ImageView>(R.id.nav_header_bg)

        doAsync {
            val userInfo: UserInfo = UserInfo().query { query -> query.equalTo("id",
                    SharedPreferenceUtils.getString(KEY_CURRENT_USER_ID)) }.first()
            uiThread {
                avatar?.let { Glide.with(this@HomeActivity).load(userInfo.profileImageUrlLarge).into(it) }
                userName?.let { it.text = userInfo.screenName }
            }
        }

        Glide.with(navBg?.context).load(R.raw.drawer_bg_4).into(navBg)
    }

    override fun onBackPressed() {
        val drawerLayout = findOptional<DrawerLayout>(R.id.drawer_layout)
        if (drawerLayout?.isDrawerOpen(Gravity.START) ?: false) {
            drawerLayout?.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }
}