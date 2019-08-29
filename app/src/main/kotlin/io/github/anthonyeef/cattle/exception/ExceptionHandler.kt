package io.github.anthonyeef.cattle.exception

import retrofit2.HttpException

/**
 *
 */
fun showException(context: androidx.fragment.app.Fragment, e: Throwable) {
    val error = tuneException(e)
    /*context.toast(error.toString())
    if (context is CatLogger) {
        context.error(error.toString())
    }*/
}

fun showException(e: Throwable) {
    val error = tuneException(e)
//    app.toast(error.toString())
}

fun Throwable.unauthorized(): Boolean {
    return this is HttpException && this.code() == 401
}

fun Throwable.forbidden(): Boolean {
    return this is HttpException && this.code() == 403
}

private fun tuneException(e: Throwable): Throwable {
    when (e) {
        is HttpException -> {
            return ApiException(e.code(), e.message())
        }
        else -> return e
    }
}
