package io.github.anthonyeef.cattle.activity

import android.arch.lifecycle.Observer
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
import io.github.anthonyeef.cattle.livedata.SharedPreferenceStringLiveData
import io.github.anthonyeef.cattle.utils.PrefUtils
import io.github.anthonyeef.cattle.utils.bindOptionalView
import io.github.anthonyeef.cattle.utils.bindView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 * HomeActivity.
 */
class HomeActivity : BaseActivity() {

    private val drawerLayout: DrawerLayout by bindView<DrawerLayout>(R.id.drawer_layout)
    private val navigation: NavigationView by bindView<NavigationView>(R.id.nav_view)
    private val toolbar: Toolbar? by bindOptionalView<Toolbar>(R.id.toolbar)
    private val viewpager: ViewPager by bindView<ViewPager>(R.id.viewpager)
    private val tabLayout: TabLayout by bindView<TabLayout>(R.id.tabs)
    private val composeBtn: FloatingActionButton by bindView<FloatingActionButton>(R.id.fab)

    private var toolbarAvatar: CircleImageView? = null
    private var navigationHeaderAvatar: CircleImageView? = null
    private var navigationHeaderUserName: TextView? = null

    private var drawerAction: Any.() -> Unit = { }
    lateinit private var drawerToggle: ActionBarDrawerToggle


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

        initUserInfoViewModel()

        composeBtn.onClick {
            startActivity(intentFor<ComposeActivity>())
        }
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun initUserInfoViewModel() {
         SharedPreferenceStringLiveData(PrefUtils.getDefaultPref(), KEY_CURRENT_USER_ID, "").observe(this, Observer {
             showUserInfoInToolbar()
             showUserInfoInDrawer()
         })
    }


    private fun showUserInfoInToolbar() {
        doAsync(exceptionHandler = {
            // todo: report to fabric
        }) {
            val userInfo: UserInfo? = Injection.provideUserInfoDao()
                    .loadUserInfoSync(PrefUtils.getString(KEY_CURRENT_USER_ID))
            activityUiThread {
                userInfo?.let {
                    Glide.with(toolbarAvatar?.context)
                            .load(it.profileImageUrlLarge)
                            .into(toolbarAvatar)
                }
            }
        }
    }


    private fun showUserInfoInDrawer() {
        doAsync(exceptionHandler = {
            // todo: report to fabric
        }) {
            val userInfo: UserInfo? = Injection.provideUserInfoDao()
                    .loadUserInfoSync(PrefUtils.getString(KEY_CURRENT_USER_ID))

            uiThread {
                userInfo?.let {
                    Glide.with(navigationHeaderAvatar?.context)
                            .load(it.profileImageUrlLarge)
                            .into(navigationHeaderAvatar)
                    navigationHeaderUserName?.text = it.screenName
                }
            }
        }
    }


    private fun setupToolbar() {
        toolbar?.let {
            setSupportActionBar(it)

            toolbarAvatar = it.findOptional(R.id.toolbar_avatar)
            toolbarAvatar?.onClick {
                if (!drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.openDrawer(Gravity.START)
                }
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        updateTitle(R.string.page_title_home)
    }


    private fun setupProfileInDrawer() {
        val navHeader = navigation.getHeaderView(0)
        val navBg = navHeader?.findOptional<ImageView>(R.id.nav_header_bg)
        navigationHeaderAvatar = navHeader?.findOptional(R.id.nav_avatar)
        navigationHeaderUserName = navHeader?.findOptional<TextView>(R.id.nav_user_name)

        showUserInfoInDrawer()

        navigationHeaderAvatar?.onClick {
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

                R.id.nav_profile -> {
                    bindDrawerAction {
                        startActivity(intentFor<ProfileActivity>(ProfileActivity.EXTRA_USER_ID to PrefUtils.getString(KEY_CURRENT_USER_ID) ))
                    }
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


    // This method only should be called when drawer is opening and trying to click some drawer item
    private fun bindDrawerAction(action: Any.() -> Unit) {
        drawerAction = action
        drawerLayout.closeDrawer(Gravity.START)
    }
}