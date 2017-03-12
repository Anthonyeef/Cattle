package io.github.anthonyeef.cattle.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_USER_ID
import io.github.anthonyeef.cattle.contract.ComposeContract
import io.github.anthonyeef.cattle.entity.UserInfo
import io.github.anthonyeef.cattle.entity.UserInfo_Table
import io.github.anthonyeef.cattle.presenter.ComposePresenter
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import io.github.anthonyeef.cattle.utils.bindOptionalView
import io.github.anthonyeef.cattle.utils.bindView
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.onClick

/**
 * ComposeFragment, where to create and edit fanfou.
 */
class ComposeFragment : BaseFragment(), ComposeContract.View {
    companion object {
        val INPUT_LIMIT = 140
    }

    lateinit var composePresenter: ComposeContract.Presenter
    val sendBtn: Button by bindView<Button>(R.id.send)
    val editContent: EditText by bindView<EditText>(android.R.id.content)
    val toolbar: Toolbar? by bindOptionalView<Toolbar>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView<LinearLayout>(R.layout.fragment_compose)
        ComposePresenter(this)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_compose, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.onClick {
            composePresenter.sendFanfou()
        }

        setupToolbar()
        setupEditText()
    }


    override fun onResume() {
        super.onResume()
        composePresenter.subscribe()
    }


    override fun onPause() {
        super.onPause()
        composePresenter.unSubscribe()
    }


    override fun setPresenter(presenter: ComposeContract.Presenter) {
        composePresenter = presenter
    }


    override fun getStatus(): String {
        return editContent.text.toString()
    }


    override fun sendFinish(success: Boolean) {
        if (success) {
            activity.finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when(id) {
            android.R.id.home -> {
                activity.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)
            val homeAvatar = it.findOptional<CircleImageView>(R.id.toolbar_avatar)
            homeAvatar?.let {
                val userInfo: UserInfo? = (select
                        from UserInfo::class
                        where (UserInfo_Table.id.eq(SharedPreferenceUtils.getString(KEY_CURRENT_USER_ID)))
                        ).list.firstOrNull()
                Glide.with(it.context)
                        .load(userInfo?.profileImageUrlLarge)
                        .into(it)
            }

            with(activity as AppCompatActivity) {
                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
        }

        updateInputCount(INPUT_LIMIT)
    }


    private fun setupEditText() {
        editContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                updateInputCount(INPUT_LIMIT - s.toString().length)
            }
        })
    }


    private fun updateInputCount(inputCount: Int) {
        val title = toolbar?.findOptional<TextView>(R.id.title)
        title?.text = inputCount.toString()
        title?.typeface = Typeface.DEFAULT // remove bold

        // TODO
    }
}