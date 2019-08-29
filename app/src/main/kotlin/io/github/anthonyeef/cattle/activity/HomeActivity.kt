package io.github.anthonyeef.cattle.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.BuildConfig
import io.github.anthonyeef.cattle.GlideApp
import io.github.anthonyeef.cattle.Injection.userInfoDb
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.adapter.ViewpagerAdapter
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_USER_ID
import io.github.anthonyeef.cattle.constant.KEY_RETROFIT_LOG_LEVEL
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.contract.HomeActivityContract
import io.github.anthonyeef.cattle.extension.addOnTabSelectedListener
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.extension.withArguments
import io.github.anthonyeef.cattle.fragment.*
import io.github.anthonyeef.cattle.livedata.SharedPreferenceStringLiveData
import io.github.anthonyeef.cattle.presenter.HomeActivityPresenter
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.utils.PrefUtils
import io.github.anthonyeef.cattle.utils.PrefUtils.defaultPref
import io.github.anthonyeef.cattle.utils.bindOptionalView
import io.github.anthonyeef.cattle.utils.bindView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * HomeActivity.
 */
class HomeActivity : BaseActivity(), HomeActivityContract.View {

    private lateinit var homePresenter: HomeActivityContract.Presenter
    private val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val navigation: NavigationView by bindView(R.id.nav_view)
    private val toolbar: Toolbar? by bindOptionalView(R.id.home_toolbar)
    private val viewpager: ViewPager by bindView(R.id.viewpager)
    private val tabLayout: TabLayout by bindView(R.id.tabs)
    private val composeBtn: FloatingActionButton by bindView(R.id.fab)

    private var toolbarAvatar: CircleImageView? = null
    private var navigationHeaderAvatar: CircleImageView? = null
    private var navigationHeaderUserName: TextView? = null

    private var drawerAction: Any.() -> Unit = { }
    private lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HomeActivityPresenter(this)

        bindView<DrawerLayout>(R.layout.activity_home)
        setContentView(R.layout.activity_home)

        setupToolbar()
        setupProfileInDrawer()
        setupDrawer()
        setupDrawerContent(navigation)
        setupViewpager()
        setupTabLayout()

        subscribeToUserInfoChanged()
        subscribeToRetrofitLogLevelChanged()

        composeBtn.setOnClickListener {
            homePresenter.composeNewFanfou()
        }
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun setPresenter(presenter: HomeActivityContract.Presenter) {
        homePresenter = presenter
    }


    override fun showComposeActivity() {
        ComposeFragment().show()
    }


    private fun subscribeToUserInfoChanged() {
         SharedPreferenceStringLiveData(defaultPref, KEY_CURRENT_USER_ID, "").observe(this, Observer {
             showUserInfoInToolbar()
             showUserInfoInDrawer()
         })
    }

    private fun subscribeToRetrofitLogLevelChanged() {
        SharedPreferenceStringLiveData(defaultPref, KEY_RETROFIT_LOG_LEVEL, "0").observe(this, Observer {
            ServiceGenerator.notifyLogLevelChanged()
        })
    }


    @SuppressLint("CheckResult")
    private fun showUserInfoInToolbar() {
        Single.fromCallable { userInfoDb.loadUserInfoSync(PrefUtils.getString(KEY_CURRENT_USER_ID)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userInfo ->
                    userInfo?.let {
                        GlideApp.with(app)
                                .load(it.profileImageUrlLarge)
                                .into(toolbarAvatar!!)
                    }
                }, {})
    }


    @SuppressLint("CheckResult")
    private fun showUserInfoInDrawer() {
        Single.fromCallable { userInfoDb.loadUserInfoSync(PrefUtils.getString(KEY_CURRENT_USER_ID)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    GlideApp.with(navigationHeaderAvatar?.context!!)
                            .load(it.profileImageUrlLarge)
                            .into(navigationHeaderAvatar!!)
                    navigationHeaderUserName?.text = it.screenName
                }, {})
    }


    private fun setupToolbar() {
        toolbar?.let {
            setSupportActionBar(it)

            toolbarAvatar = it.findViewById(R.id.toolbar_avatar)
            toolbarAvatar?.setOnClickListener {
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
        val navBg = navHeader?.findViewById<ImageView>(R.id.nav_header_bg)
        navigationHeaderAvatar = navHeader?.findViewById(R.id.nav_avatar)
        navigationHeaderUserName = navHeader?.findViewById(R.id.nav_user_name)

        showUserInfoInDrawer()

        navigationHeaderAvatar?.setOnClickListener { view ->
            bindDrawerAction {
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        GlideApp.with(navBg?.context!!)
                .load(R.raw.drawer_bg_3)
                .into(navBg)
    }


    private fun setupDrawer() {
        drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                composeBtn.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                    override fun onHidden(fab: FloatingActionButton?) {
                        super.onHidden(fab)
                        fab?.visibility = View.INVISIBLE
                    }
                })
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                composeBtn.show()
                this@HomeActivity.run(drawerAction)
                drawerAction = { }
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
    }


    private fun setupDrawerContent(navigation: NavigationView) {
        navigation.menu.findItem(R.id.nav_test_field).isVisible = BuildConfig.DEBUG
        navigation.menu.findItem(R.id.nav_test_profile).isVisible = BuildConfig.DEBUG
        navigation.menu.findItem(R.id.nav_debug_setting).isVisible = BuildConfig.DEBUG

        navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_test_field -> {
                    bindDrawerAction {
                        TestFieldFragment().show()
                    }
                }

                R.id.nav_profile -> {
                    bindDrawerAction {
                        ProfileFragment()
                            .withArguments(ProfileFragment.KEY_USER_ID to PrefUtils.getString(KEY_CURRENT_USER_ID))
                            .show()
                    }
                }

                R.id.nav_setting -> {
                    bindDrawerAction {
                        SettingFragment().show()
                    }
                }

                R.id.nav_test_profile -> {
                    bindDrawerAction {
                        ProfileFragment()
                            .withArguments(ProfileFragment.KEY_USER_ID to "臬兀")
                            .show()
                    }
                }

                R.id.nav_debug_setting -> {
                    bindDrawerAction {
                      DebugSettingFragment().show()
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
        val title = toolbar?.findViewById<TextView>(R.id.title)
        title?.text = getString(pageName)
    }


    // This method only should be called when drawer is opening and trying to click some drawer item
    private fun bindDrawerAction(action: Any.() -> Unit) {
        drawerAction = action
        drawerLayout.closeDrawer(Gravity.START)
    }
}