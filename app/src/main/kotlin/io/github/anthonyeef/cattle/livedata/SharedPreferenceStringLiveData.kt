package io.github.anthonyeef.cattle.livedata

import android.content.SharedPreferences

/**
 * string [SharedPreferenceLiveData]
 */
class SharedPreferenceStringLiveData(sharedPrefs: SharedPreferences, key: String, defValue: String) : SharedPreferenceLiveData<String>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: String): String = sharedPref.getString(key, defValue)
}