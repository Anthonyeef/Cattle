package io.github.anthonyeef.cattle.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.databinding.ViewProfileHeaderCountBinding
import io.github.anthonyeef.cattle.entity.UserProfileDataEntity
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.verticalPadding

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
            this.onClick {
                v.operation.invoke()
            }
        }

    private var headerCountBinding: ViewProfileHeaderCountBinding? = null


    init {
        headerCountBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_profile_header_count, this, true)
        orientation = VERTICAL
        gravity = Gravity.LEFT
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        verticalPadding = dimen(R.dimen.vertical_padding_small)
    }
}