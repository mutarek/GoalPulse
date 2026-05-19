package com.prosolution.goalpulse.localization

import android.content.Context
import androidx.annotation.StringRes
import java.util.Locale

object LocalizationHelper {
    fun getString(
        context: Context,
        @StringRes stringResId: Int,
        languageCode: String
    ): String {
        return try {
            // Create a locale-specific configuration
            val locale = Locale.forLanguageTag(languageCode)
            val configuration = context.resources.configuration.apply {
                setLocale(locale)
            }
            val localizedContext = context.createConfigurationContext(configuration)
            localizedContext.getString(stringResId)
        } catch (e: Exception) {
            // Fallback to default
            context.getString(stringResId)
        }
    }
}

