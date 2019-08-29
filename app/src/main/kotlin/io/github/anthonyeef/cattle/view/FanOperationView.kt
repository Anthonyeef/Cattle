package io.github.anthonyeef.cattle.view

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import io.github.anthonyeef.cattle.databinding.ViewFanOperationBinding

/**
 * Fan operation:
 *
 * - replay
 * - repost
 * - favourite
 */
class FanOperationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {
    private val fanOperationBinding: ViewFanOperationBinding

    init {
        fanOperationBinding = ViewFanOperationBinding.inflate(LayoutInflater.from(context), this@FanOperationView, true)
    }
}