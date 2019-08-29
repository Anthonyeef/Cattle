package io.github.anthonyeef.cattle.view

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.updatePadding
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.databinding.ViewProfileHeaderCountBinding
import io.github.anthonyeef.cattle.entity.UserProfileDataEntity
import io.github.anthonyeef.cattle.utils.dp2px

/**
 *
 */
class ProfileHeaderCountView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
): LinearLayout(context, attrs, defStyle) {

    var userProfileData: UserProfileDataEntity
        get() = throw UnsupportedOperationException()
        set(v) {
            headerCountBinding?.profileData = v
            this.setOnClickListener {
                v.operation.invoke()
            }
        }

    private var headerCountBinding: ViewProfileHeaderCountBinding? = null


    init {
        headerCountBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_profile_header_count, this, true)
        orientation = VERTICAL
        gravity = Gravity.LEFT
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        updatePadding(top = dp2px(context, 8), bottom = dp2px(context, 8))
    }
}