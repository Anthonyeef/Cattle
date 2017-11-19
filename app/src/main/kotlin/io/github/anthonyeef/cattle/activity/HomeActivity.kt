package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.Injection
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.adapter.ViewpagerAdapter
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_USER_ID
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.extension.addOnTabSelectedListener
import io.github.anthonyeef.cattle.fragment.BaseListFragment
import io.github.anthonyeef.cattle.fragment.DirectMessageInboxFragment
import io.github.anthonyeef.cattle.fragment.HomeFeedListFragment
import io.github.anthonyeef.cattle.fragment.MentionListFragment
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import io.github.anthonyeef.cattle.utils.bindOptionalView
import io.github.anthonyeef.cattle.utils.bindView
import org.jetbrains.anko.*

/**
 * HomeActivity.
 */
class HomeActivity : BaseActivity() {

    val drawerLayout: DrawerLayout by bindView<DrawerLayout>(R.id.drawer_layout)

    val navigation: NavigationView by bindView<NavigationView>(R.id.nav_view)

    val toolbar: Toolbar? by bindOptionalView<Toolbar>(R.id.toolbar)

    val viewpager: ViewPager by bindView<ViewPager>(R.id.viewpager)

    val tabLayout: TabLayout by bindView<TabLayout>(R.id.tabs)

    val composeBtn: FloatingActionButton by bindView<FloatingActionButton>(R.id.fab)

    var drawerAction: Any.() -> Unit = { }

    lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView<DrawerLayout>(R.layout.activity_home)
        setContentView(R.layout.activity_home)

        setupToolbar()
        setupProfileInDrawer()
        setupDrawer()
        setupDrawerContent(navigation)
        setupViewpager()
        setupTabLayout()

        composeBtn.onClick {
            startActivity(intentFor<ComposeActivity>())
        }
    }


    private fun setupToolbar() {
        toolbar?.let {
            setSupportActionBar(it)

            val homeAvatar = it.findOptional<CircleImageView>(R.id.toolbar_avatar)
            homeAvatar?.let { avatar ->
                doAsync {
                    val userInfo: UserInfo? = Injection.provideUserInfoDao()
                            .getUserInfoById(SharedPreferenceUtils.getString(KEY_CURRENT_USER_ID))

                    uiThread {
                        Glide.with(avatar.context)
                                .load(userInfo?.profileImageUrlLarge)
                                .into(avatar)
                        avatar.onClick {
                            if (!drawerLayout.isDrawerOpen(Gravity.START)) {
                                drawerLayout.openDrawer(Gravity.START)
                            }
                        }
                    }
                }
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        updateTitle(R.string.page_title_home)
    }


    private fun setupProfileInDrawer() {
        val navHeader = navigation.getHeaderView(0)
        val avatar = navHeader?.findOptional<CircleImageView>(R.id.nav_avatar)
        val userName = navHeader?.findOptional<TextView>(R.id.nav_user_name)
        val navBg = navHeader?.findOptional<ImageView>(R.id.nav_header_bg)

        doAsync {
            val userInfo: UserInfo? = Injection.provideUserInfoDao()
                    .getUserInfoById(SharedPreferenceUtils.getString(KEY_CURRENT_USER_ID))

            uiThread {
                userInfo?.let {
                    Glide.with(avatar?.context)
                            .load(it.profileImageUrlLarge)
                            .into(avatar)
                    userName?.text = it.screenName
                }
            }
        }

        avatar?.onClick {
            bindDrawerAction { startActivity(intentFor<LoginActivity>()) }
        }

        Glide.with(navBg?.context).load(R.raw.drawer_bg_3).into(navBg)
    }


    private fun setupDrawer() {
        drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)

                composeBtn.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                    override fun onHidden(fab: FloatingActionButton?) {
                        super.onHidden(fab)
                        fab?.visibility = View.INVISIBLE
                    }
                })
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)

                composeBtn.show()
                this@HomeActivity.run(drawerAction)
                drawerAction = { }
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
    }


    private fun setupDrawerContent(navigation: NavigationView) {
        navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_test_field -> {
                    bindDrawerAction { startActivity(intentFor<TestFieldActivity>()) }
                }
                else -> {

                }
            }
            drawerLayout.closeDrawer(Gravity.START)
            false
        }
    }


    private fun setupViewpager() {
        viewpager.offscreenPageLimit = 2
        val adapter = ViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFeedListFragment(), R.string.page_title_home)
        adapter.addFragment(MentionListFragment(), R.string.page_title_mention)
        adapter.addFragment(DirectMessageInboxFragment(), R.string.page_title_direct_message)

        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                updateTitle(adapter.getTitleForToolbar(position))
            }
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
        })
    }


    private fun setupTabLayout() {
        with(tabLayout) {
            setupWithViewPager(viewpager)
            getTabAt(0)?.setIcon(R.drawable.icon_home)
            getTabAt(1)?.setIcon(R.drawable.icon_ringbell)
            getTabAt(2)?.setIcon(R.drawable.icon_mail)
            addOnTabSelectedListener {
                onTabReSelected {
                    ((viewpager.adapter as ViewpagerAdapter).getItem(it.position) as BaseListFragment).doScrollToTop()
                }
            }
        }
    }


    private fun updateTitle(@StringRes pageName: Int) {
        val title = toolbar?.findOptional<TextView>(R.id.title)
        title?.text = getString(pageName)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }


    // This method only should be called when drawer is opening and trying to click some drawer item
    private fun bindDrawerAction(action: Any.() -> Unit) {
        drawerAction = action
        drawerLayout.closeDrawer(Gravity.START)
    }
}