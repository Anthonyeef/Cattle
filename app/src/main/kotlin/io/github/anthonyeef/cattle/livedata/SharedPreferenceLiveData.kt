package io.github.anthonyeef.cattle.livedata

import androidx.lifecycle.LiveData
import android.content.SharedPreferences

/**
 * livedata to observe sharedPreference value
 */
abstract class SharedPreferenceLiveData<T>(val sharedPref: SharedPreferences,
                                           private val key: String,
                                           private val defValue: T) : LiveData<T>() {

    abstract fun getValueFromPreferences(key: String, defValue: T): T


    override fun onActive() {
        super.onActive()

        if (key == this.key) {
            val newValue = getValueFromPreferences(key, defValue)
            if (value != newValue) {
                value = newValue
            }
        }
        sharedPref.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }


    override fun onInactive() {
        sharedPref.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }


    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == this.key) {
            val newValue = getValueFromPreferences(key, defValue)
            if (value != newValue) {
                value = newValue
            }
        }
    }
}