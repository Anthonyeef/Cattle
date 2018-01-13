package io.github.anthonyeef.cattle.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.GlideApp
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.contract.ProfileContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.extension.gone
import io.github.anthonyeef.cattle.utils.LoadMoreDelegate
import io.github.anthonyeef.cattle.view.ProfileHeaderCountView
import io.github.anthonyeef.cattle.viewbinder.ProfileStatusViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.info

/**
 * Personal info fragment. Display personal info, fanfou list, fav fanfou, etc.
 */
class ProfileFragment : BaseFragment(),
        ProfileContract.View,
        LoadMoreDelegate.LoadMoreSubject {

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }

    private var profilePresenter: ProfileContract.Presenter? = null
    private var profileBackground: ImageView? = null
    private var profileAvatar: CircleImageView? = null
    private var profileUserName: TextView? = null
    private var profileDescription: TextView? = null
    private var profileAppbar: AppBarLayout? = null
    private var profileToolbarLayout: CollapsingToolbarLayout? = null
    private var profileToolbar: Toolbar? = null
    private var userStatusList: RecyclerView? = null
    private var items: Items = Items()

    private var followingCount: ProfileHeaderCountView? = null
    private var followerCount: ProfileHeaderCountView? = null
    private var statusCount: ProfileHeaderCountView? = null

    private val adapter: MultiTypeAdapter by lazy { MultiTypeAdapter(items).apply { register(Status::class.java, ProfileStatusViewBinder()) } }

    lateinit private var loadMoreDelegate: LoadMoreDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMoreDelegate = LoadMoreDelegate(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.frag_profile, container, false)
        profileBackground = contentView?.find(R.id.profile_bg)
        profileAvatar = contentView?.find(R.id.avatar)
        profileUserName = contentView?.find(R.id.user_display_name)
        profileDescription = contentView?.find(R.id.description)
        profileAppbar = contentView?.find(R.id.appbar)
        profileToolbarLayout = contentView?.find(R.id.toolbar_layout)
        profileToolbar = contentView?.find(R.id.toolbar)
        followingCount = contentView?.find(R.id.following_count)
        followerCount = contentView?.find(R.id.follower_count)
        statusCount = contentView?.find(R.id.status_count)
        userStatusList = contentView?.find(android.R.id.list)

        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMoreDelegate.attach(this)

        profileToolbar?.let {
            (activity as? AppCompatActivity)?.setSupportActionBar(it)
        }
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        profileToolbarLayout?.title = " "

        userStatusList?.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        profilePresenter?.subscribe()
    }


    override fun onPause() {
        super.onPause()
        profilePresenter?.unSubscribe()
    }


    override fun setPresenter(presenter: ProfileContract.Presenter?) {
        profilePresenter = presenter
    }


    @SuppressLint("SetTextI18n")
    override fun showProfile(userInfo: UserInfo) {
        GlideApp.with(profileBackground?.context)
                .asDrawable()
                .load(userInfo.profileBackgroundImageUrl)
                .into(profileBackground)

        GlideApp.with(profileAvatar?.context)
                .load(userInfo.profileImageUrlLarge)
                .into(profileAvatar)

        profileUserName?.text = userInfo.screenName

        if (userInfo.description.isNotEmpty()) {
            profileDescription?.text = getString(R.string.text_self_intro) + "\n" + userInfo.description.trimIndent()
        } else {
            profileDescription?.gone()
        }

        // todo: implementation for real page
        followingCount?.userProfileData = ProfileHeaderCountView.UserProfileDataEntity(
                itemName = getString(R.string.text_following),
                countNumber = userInfo.friendsCount,
                operation = { info("clicked following count")
                })
        followerCount?.userProfileData = ProfileHeaderCountView.UserProfileDataEntity(
                itemName = getString(R.string.text_follower),
                countNumber = userInfo.followersCount,
                operation = { info("clicked follower count")
                })
        statusCount?.userProfileData = ProfileHeaderCountView.UserProfileDataEntity(
                itemName = getString(R.string.text_status),
                countNumber = userInfo.statusesCount,
                operation = { info("clicked status count")
                })
    }


    override fun showStatuses(statuses: List<Status>) {
        items.addAll(statuses)
        adapter.notifyDataSetChanged()
    }


    override fun isLoading(): Boolean {
        return profilePresenter?.isStatusLoading() ?: false
    }


    override fun onLoadMore() {
        if (isLoading().not()) {
            profilePresenter?.loadStatuses()
        }
    }
}