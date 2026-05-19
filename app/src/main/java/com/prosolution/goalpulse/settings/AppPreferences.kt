package com.prosolution.goalpulse.settings

import android.content.Context

private const val PREFS_NAME = "goalpulse_prefs"
private const val KEY_DARK_MODE = "dark_mode"
private const val KEY_LANGUAGE = "language"

data class AppSettings(
    val darkModeEnabled: Boolean,
    val languageCode: String
)

object AppPreferences {
    fun load(context: Context): AppSettings {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return AppSettings(
            darkModeEnabled = prefs.getBoolean(KEY_DARK_MODE, true),
            languageCode = prefs.getString(KEY_LANGUAGE, "en").orEmpty().ifBlank { "en" }
        )
    }

    fun saveDarkMode(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_DARK_MODE, enabled)
            .apply()
    }

    fun saveLanguage(context: Context, languageCode: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, languageCode)
            .apply()
    }
}

