package io.github.anthonyeef.cattle.utils

import android.content.Context.MODE_PRIVATE
import io.github.anthonyeef.cattle.constant.app

/**
 * A utils class for easy SharePreference operation.
 */
object SharedPreferenceUtils {

    fun getString(key: String): String {
        return app.getSharedPreferences(key, MODE_PRIVATE).getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return app.getSharedPreferences(key, MODE_PRIVATE).getString(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        val sharePreferences = app.getSharedPreferences(key, MODE_PRIVATE)
        val editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}