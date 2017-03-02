package io.github.anthonyeef.cattle.exception

import android.support.v4.app.Fragment
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.github.anthonyeef.cattle.utils.CatLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.toast

/**
 *
 */
fun showException(context: Fragment, e: Throwable) {
    val error = tuneException(e)
    context.toast(error.toString())
    if (context is CatLogger) {
        context.error(error.toString())
    }
}

private fun tuneException(e: Throwable): Throwable {
    when (e) {
        is HttpException -> {
            return ApiException(e.code(), e.message())
        }
        else -> return e
    }
}
