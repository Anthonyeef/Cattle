package io.github.anthonyeef.cattle.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import io.github.anthonyeef.cattle.constant.OPERATION_BTN_SIZE_SMALL
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.ViewFanOperationGroupBinding
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.fragment.ComposeFragment
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.support.v4.withArguments

class FanOperationViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {
    private val viewBinding: ViewFanOperationGroupBinding = ViewFanOperationGroupBinding.inflate(LayoutInflater.from(context), this, true)
    private val defaultSize = OPERATION_BTN_SIZE_SMALL
    private var targetStatus: Status? = null

    init {
        viewBinding.size = defaultSize

        viewBinding.btnRetweet.onClick {
            ComposeFragment().withArguments(ComposeFragment.KEY_REPOST_ID to status?.id).show()
        }
    }


    var size: Int
        set(v) {
            viewBinding.size = v
        }
        get() = throw UnsupportedOperationException("get method for operation item size is not supported")


    var status: Status?
        set(v) {
            targetStatus = v
        }
        get() = targetStatus
}