package io.github.anthonyeef.cattle.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.github.anthonyeef.cattle.R
import org.jetbrains.anko.dimen
import org.jetbrains.anko.find
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
            countNumber?.text = v.countNumber.toString()
            countName?.text = v.itemName

            onClick {
                v.operation.invoke()
            }
        }

    private var countNumber: TextView? = null
    private var countName: TextView? = null


    init {
        View.inflate(context, R.layout.view_profile_header_count, this)
        orientation = VERTICAL
        gravity = Gravity.LEFT
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        verticalPadding = dimen(R.dimen.vertical_padding_small)

        countNumber = find(R.id.count_number)
        countName = find(R.id.count_name)
    }


    class UserProfileDataEntity(val itemName: String, val countNumber: Int = 0, val operation: () -> Unit)
}