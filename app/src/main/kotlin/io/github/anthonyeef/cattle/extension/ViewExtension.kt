package io.github.anthonyeef.cattle.extension

import android.view.View

/**
 * Some view extension method for usage.
 */
fun <T: View> T.gone(): Unit {
    this.visibility = View.GONE
}

fun <T: View> T.goneIf(predicate: Boolean) {
    this.visibility = if (predicate) View.GONE else View.VISIBLE
}

fun <T: View> T.show(): Unit {
    this.visibility = View.VISIBLE
}
