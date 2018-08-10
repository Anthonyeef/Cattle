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
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.GlideApp
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.FollowerListActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.contract.ProfileContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.entity.DummyListViewEntity
import io.github.anthonyeef.cattle.entity.ListHeaderViewEntity
import io.github.anthonyeef.cattle.entity.PreviewAlbumPhotos
import io.github.anthonyeef.cattle.entity.UserProfileDataEntity
import io.github.anthonyeef.cattle.exception.forbidden
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.exception.unauthorized
import io.github.anthonyeef.cattle.extension.gone
import io.github.anthonyeef.cattle.presenter.ProfilePresenter
import io.github.anthonyeef.cattle.utils.LoadMoreDelegate
import io.github.anthonyeef.cattle.view.ProfileHeaderCountView
import io.github.anthonyeef.cattle.viewbinder.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

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

  private var statusTotalCount: Int = 0
  private var statusLoadError: Boolean = false
  private var hasSetLayoutAnimation = false

  private val adapter: MultiTypeAdapter by lazy { MultiTypeAdapter(items).apply {
    register(DummyListViewBinder())
    register(ListHeaderViewBinder())
    register(ProfileStatusViewBinder())
    register(ProfilePreviewAlbumListViewBinder())
    register(ProfileLockedViewBinder())
  } }

  private lateinit var loadMoreDelegate: LoadMoreDelegate

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    loadMoreDelegate = LoadMoreDelegate(this)

    val uid = arguments?.getString(KEY_USER_ID)
    uid?.let {
      ProfilePresenter(this, it)
    }
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

    profilePresenter?.loadAlbumPreview()
    profilePresenter?.loadProfile(false)
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
    GlideApp.with(profileBackground?.context!!)
        .asDrawable()
        .load(userInfo.profileBackgroundImageUrl)
        .into(profileBackground!!)

    GlideApp.with(profileAvatar?.context!!)
        .load(userInfo.profileImageUrlLarge)
        .into(profileAvatar!!)

    profileUserName?.text = userInfo.screenName

    if (userInfo.description.isNotEmpty()) {
      profileDescription?.text = getString(R.string.text_self_intro) + "\n" + userInfo.description.trimIndent()
    } else {
      profileDescription?.gone()
    }

    // todo: implementation for real page
    followingCount?.userProfileData = UserProfileDataEntity(
        itemName = getString(R.string.text_following),
        countNumber = userInfo.friendsCount,
        operation = { info("clicked following count")
        })
    followerCount?.userProfileData = UserProfileDataEntity(
        itemName = getString(R.string.text_follower),
        countNumber = userInfo.followersCount,
        operation = {
          startActivity(app.intentFor<FollowerListActivity>(FollowerListActivity.KEY_USER_ID to userInfo.id ))
        })
    statusCount?.userProfileData = UserProfileDataEntity(
        itemName = getString(R.string.text_favourite),
        countNumber = userInfo.favouritesCount,
        operation = { info("clicked favourite count")
        })

    statusTotalCount = userInfo.statusesCount
  }


  override fun showAlbumPreview(photos: List<Status>) {
    if (photos.isNotEmpty()) {
      items.add(0, DummyListViewEntity(showTopDivider = false))
      items.add(1, PreviewAlbumPhotos(photos))
    }
  }


  override fun showStatuses(statuses: List<Status>) {
    // only add list title once
    val isFirstLoad = items.any { it is Status }.not()
    if (isFirstLoad) {
      items.add(2, ListHeaderViewEntity(getString(R.string.title_status_list, statusTotalCount)))
    }

    if (statuses.isNotEmpty()) {
      val insertedPosition = items.size
      items.addAll(statuses)
      adapter.notifyItemInserted(insertedPosition)
    }
  }


  override fun showError(error: Throwable) {
    if (error.forbidden()) {
      statusLoadError = true
      items.add(ProfileLockedViewBinder.ProfileLockedDummyEntity())
      adapter.notifyDataSetChanged()
    } else if (error.unauthorized()) {
      showException(error)
    }
  }


  override fun isLoading(): Boolean {
    return profilePresenter?.isStatusLoading() ?: false
  }


  override fun onLoadMore() {
    if (isLoading().not() && statusLoadError.not()) {
      profilePresenter?.loadStatuses()
    }
  }


  private fun runLayoutAnimation() {
    if (hasSetLayoutAnimation.not()) {
      val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
      userStatusList?.layoutAnimation = animation
      hasSetLayoutAnimation = true
    }
    userStatusList?.scheduleLayoutAnimation()
  }
}