package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import io.github.anthonyeef.cattle.R

/**
 *
 */
class DebugSettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_debug_preferences)
    }
}