package io.github.anthonyeef.cattle.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.uber.autodispose.AutoDispose.autoDisposable
import io.github.anthonyeef.cattle.CattleSchedulers.io
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.Injection.statusDb
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.horizontalPaddingMedium
import io.github.anthonyeef.cattle.contract.ComposeContract
import io.github.anthonyeef.cattle.presenter.ComposePresenter
import io.github.anthonyeef.cattle.utils.bindOptionalView
import io.github.anthonyeef.cattle.utils.bindView
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.wrapContent

/**
 * ComposeFragment, where to create and edit fanfou.
 */
class ComposeFragment : BaseFragment(), ComposeContract.View {
    companion object {
        const val INPUT_LIMIT = 140
        const val KEY_REPOST_ID = "key_repost_id"
    }

    private lateinit var composePresenter: ComposeContract.Presenter
    private val sendBtn: Button by bindView(R.id.send)
    private val editContent: EditText by bindView(android.R.id.content)
    private val toolbar: Toolbar? by bindOptionalView(R.id.toolbar)
    private val inputLimit: TextView? by bindOptionalView(R.id.title)
    private val repostFanfouId: String by lazy { arguments?.getString(KEY_REPOST_ID) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView<LinearLayout>(R.layout.fragment_compose)
        ComposePresenter(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.onClick {
            if (repostFanfouId.isNotEmpty()) {
                composePresenter.repostFanfou()
            } else {
                composePresenter.sendFanfou()
            }
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


    override fun getRepostStatusId(): String = repostFanfouId


    override fun sendFinish(success: Boolean) {
        if (success) {
            activity?.finish()
        }
    }


    private fun setupToolbar() {
        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            with(activity as AppCompatActivity) {
                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }

            it.setNavigationOnClickListener { _ ->
                activity?.onBackPressed()
            }
        }

        // move to left little bit
        val layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
        layoutParams.leftMargin = horizontalPaddingMedium
        inputLimit?.layoutParams = layoutParams

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
        if (repostFanfouId.isNotEmpty()) {
          statusDb.getStatusById(repostFanfouId)
              .subscribeOn(io)
              .observeOn(mainThread)
              .`as`(autoDisposable(lifeScope))
              .subscribe {
                editContent.setText(it.toString(), TextView.BufferType.EDITABLE)
              }
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }


    private fun updateInputCount(inputCount: Int) {
        inputLimit?.text = inputCount.toString()
        inputLimit?.typeface = Typeface.DEFAULT // remove bold

        // TODO
    }
}