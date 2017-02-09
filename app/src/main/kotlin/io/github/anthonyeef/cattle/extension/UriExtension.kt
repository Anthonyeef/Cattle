package io.github.anthonyeef.cattle.extension

import android.net.Uri

/**
 * Some extension method for Uri usage.
 */
fun String.getQueryParameter(key: String): String {
    return Uri.EMPTY.buildUpon().encodedQuery(this).build().getQueryParameter(key)
}
