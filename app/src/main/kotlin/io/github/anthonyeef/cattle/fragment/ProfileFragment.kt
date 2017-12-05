package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import io.github.anthonyeef.cattle.contract.ProfileContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.view.ProfileItemViewBinder
import io.github.anthonyeef.cattle.view.ProfileStatusViewBinder

/**
 * Personal info fragment. Display personal info, fanfou list, fav fanfou, etc.
 */
class ProfileFragment : BaseListFragment(), ProfileContract.View {

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }

    private var profilePresenter: ProfileContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(UserInfo::class.java, ProfileItemViewBinder())
        adapter.register(Status::class.java, ProfileStatusViewBinder())
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


    override fun showProfile(userInfo: UserInfo) {
        items.clear()
        items.add(userInfo)
        adapter.notifyDataSetChanged()
    }


    override fun showStatuses(statuses: List<Status>) {
        items.addAll(statuses)
        adapter.notifyDataSetChanged()
    }
}