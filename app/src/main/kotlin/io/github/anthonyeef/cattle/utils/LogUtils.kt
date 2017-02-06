package io.github.anthonyeef.cattle.utils

import android.util.Log

/**
 * Utils for easy log usage.
 */
object LogUtils {
    fun e(tag: String, exception: Exception) {
        Log.e(tag, exception.toString())
    }

    fun e(tag: String, throwable: Throwable?) {
        Log.e(tag, throwable.toString())
    }

    fun e(tag: String, string: String) {
        Log.e(tag, string)
    }

    fun d(tag: String, exception: Exception) {
        Log.d(tag, exception.toString())
    }

    fun d(tag: String, throwable: Throwable) {
        Log.d(tag, throwable.toString())
    }

    fun d(tag: String, string: String) {
        Log.d(tag, string)
    }
}