package io.github.anthonyeef.cattle.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.content.edit
import io.github.anthonyeef.cattle.constant.KEY_CURRENT_LANGUAGE
import java.util.*

object LocaleUtils {

  fun onAttach(baseContext: Context): Context {
    return setLocale(baseContext, getSavedLanguage(baseContext))
  }


  fun setLocale(context: Context, language: String): Context {
    saveLanguage(context, language)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return updateResources(context, language)
    }

    return updateResourcesLegacy(context, language)
  }


  private fun saveLanguage(context: Context, language: String) {
    PreferenceManager.getDefaultSharedPreferences(context).edit { putString(KEY_CURRENT_LANGUAGE, language) }
  }


  private fun getSavedLanguage(context: Context): String {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_CURRENT_LANGUAGE, Locale.getDefault().language)
  }


  @TargetApi(Build.VERSION_CODES.N)
  private fun updateResources(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val configuration = context.resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)

    return context.createConfigurationContext(configuration)
  }


  @Suppress("DEPRECATION")
  private fun updateResourcesLegacy(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val resource = context.resources
    val configuration = resource.configuration
    configuration.locale = locale
    configuration.setLayoutDirection(locale)

    resource.updateConfiguration(configuration, resource.displayMetrics)

    return context
  }

}