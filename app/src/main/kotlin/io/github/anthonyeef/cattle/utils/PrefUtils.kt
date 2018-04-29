package io.github.anthonyeef.cattle.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.content.edit
import io.fabric.sdk.android.services.network.HttpRequest.put
import io.github.anthonyeef.cattle.constant.app
import org.jetbrains.anko.AnkoLogger

/**
 * A utils class for easy SharePreference operation.
 */
object PrefUtils : AnkoLogger {
  val defaultPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

  fun getString(key: String): String {
    return defaultPref.getString(key, "")
  }

  fun getString(key: String, defaultValue: String): String {
    return defaultPref.getString(key, defaultValue)
  }

  fun getLong(key: String, defaultValue: Long): Long {
    return defaultPref.getLong(key, defaultValue)
  }
}
