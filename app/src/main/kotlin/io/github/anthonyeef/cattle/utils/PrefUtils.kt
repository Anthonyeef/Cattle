package io.github.anthonyeef.cattle.utils

import android.preference.PreferenceManager
import io.github.anthonyeef.cattle.constant.app
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A utils class for easy SharePreference operation.
 */
object PrefUtils : AnkoLogger {

    fun getDefaultPref() = PreferenceManager.getDefaultSharedPreferences(app)

    fun getString(key: String): String {
        return getDefaultPref().getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return getDefaultPref().getString(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return getDefaultPref().getLong(key, defaultValue)
    }

    fun put(key: String, value: Any) {
        val sharePreferences = getDefaultPref()
        val editor = sharePreferences.edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }

            is Long -> {
                editor.putLong(key, value)
            }

            else -> {
                info("Unknown type: " + value.javaClass.toString())
            }
        }

        editor.apply()
    }
}