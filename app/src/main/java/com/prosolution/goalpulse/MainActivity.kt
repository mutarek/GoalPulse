package com.prosolution.goalpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.prosolution.designsystem.GoalPulseTheme
import com.prosolution.goalpulse.navigation.GoalPulseNavHost
import com.prosolution.goalpulse.settings.AppPreferences
import com.prosolution.goalpulse.ui.splash.BrandedSplashScreen

private val supportedLanguageCodes = setOf("en", "bn", "es", "hi")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val initialSettings = AppPreferences.load(this)
        val initialLanguageCode = initialSettings.languageCode.takeIf { it in supportedLanguageCodes } ?: "en"
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(initialLanguageCode)
        )

        setContent {
            var showBrandedSplash by remember { mutableStateOf(true) }
            var darkModeEnabled by remember { mutableStateOf(initialSettings.darkModeEnabled) }
            var languageCode by remember { mutableStateOf(initialLanguageCode) }

            LaunchedEffect(Unit) {
                delay(1400)
                showBrandedSplash = false
            }

            LaunchedEffect(languageCode) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageCode)
                )
            }

            if (showBrandedSplash) {
                BrandedSplashScreen()
            } else {
                GoalPulseTheme(darkTheme = darkModeEnabled) {
                    GoalPulseNavHost(
                        darkModeEnabled = darkModeEnabled,
                        selectedLanguageCode = languageCode,
                        onDarkModeChanged = { enabled ->
                            darkModeEnabled = enabled
                            AppPreferences.saveDarkMode(this, enabled)
                        },
                        onLanguageSelected = { selectedCode ->
                            val safeCode = selectedCode.takeIf { it in supportedLanguageCodes } ?: "en"
                            languageCode = safeCode
                            AppPreferences.saveLanguage(this, safeCode)
                        }
                    )
                }
            }
        }
    }
}
