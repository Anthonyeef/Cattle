package io.github.anthonyeef.cattle.utils

import android.content.Context

/**
 * @author wuyifen @ Zhihu Inc.
 * @since 08-29-2019
 */

fun dp2px(context: Context?, dp: Int): Int {
    if (context == null) return 0
    val density = context.resources.displayMetrics.density
    return (dp * density + 0.5f).toInt()
}