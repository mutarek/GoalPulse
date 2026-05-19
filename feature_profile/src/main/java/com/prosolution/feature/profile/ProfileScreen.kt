package com.prosolution.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.annotation.StringRes
import com.prosolution.designsystem.GoalPulseAccentCard
import com.prosolution.designsystem.GoalPulseBackground
import com.prosolution.designsystem.GoalPulseBadge
import com.prosolution.designsystem.GoalPulseHeroCard
import com.prosolution.designsystem.GoalPulseSectionHeader

private data class LanguageOption(
    val code: String,
    @param:StringRes val labelRes: Int,
    val nativeLabel: String
)

private val supportedLanguages = listOf(
    LanguageOption(code = "en", labelRes = R.string.language_english, nativeLabel = "English"),
    LanguageOption(code = "bn", labelRes = R.string.language_bangla, nativeLabel = "Bangla"),
    LanguageOption(code = "es", labelRes = R.string.language_spanish, nativeLabel = "Espanol"),
    LanguageOption(code = "hi", labelRes = R.string.language_hindi, nativeLabel = "Hindi")
)

@Composable
fun ProfileRoute(
    darkModeEnabled: Boolean,
    selectedLanguageCode: String,
    onDarkModeChanged: (Boolean) -> Unit,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Force recomposition when locale changes by reading configuration
    LocalConfiguration.current.locales

    key(selectedLanguageCode) {
        GoalPulseBackground(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GoalPulseHeroCard(
                    title = localizedString(R.string.profile_title, selectedLanguageCode),
                    subtitle = localizedString(R.string.profile_subtitle, selectedLanguageCode),
                    footer = localizedString(R.string.profile_footer, selectedLanguageCode)
                )
            }

            item {
                GoalPulseSectionHeader(
                    title = localizedString(R.string.profile_preferences_title, selectedLanguageCode),
                    subtitle = localizedString(R.string.profile_preferences_subtitle, selectedLanguageCode)
                )
            }

            item {
                GoalPulseAccentCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GoalPulseBadge(text = localizedString(R.string.profile_appearance_badge, selectedLanguageCode))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = localizedString(R.string.profile_dark_mode_title, selectedLanguageCode),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = if (darkModeEnabled) {
                                        localizedString(R.string.profile_dark_mode_enabled_desc, selectedLanguageCode)
                                    } else {
                                        localizedString(R.string.profile_dark_mode_disabled_desc, selectedLanguageCode)
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                                )
                            }
                            Switch(
                                checked = darkModeEnabled,
                                onCheckedChange = onDarkModeChanged
                            )
                        }
                    }
                }
            }

            item {
                GoalPulseAccentCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GoalPulseBadge(text = localizedString(R.string.profile_language_badge, selectedLanguageCode))
                        Text(
                            text = localizedString(R.string.profile_language_desc, selectedLanguageCode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        LanguageDropdown(
                            selectedLanguageCode = selectedLanguageCode,
                            onLanguageSelected = onLanguageSelected
                        )
                    }
                }
            }

            item {
                GoalPulseAccentCard {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        GoalPulseBadge(text = localizedString(R.string.profile_favorites_badge, selectedLanguageCode))
                        Text(
                            text = localizedString(R.string.profile_favorites_desc, selectedLanguageCode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                GoalPulseAccentCard {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        GoalPulseBadge(text = localizedString(R.string.profile_notifications_badge, selectedLanguageCode))
                        Text(
                            text = localizedString(R.string.profile_notifications_desc, selectedLanguageCode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
        }
    }
}

@Composable
private fun LanguageDropdown(
    selectedLanguageCode: String,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedLanguage = supportedLanguages.find { it.code == selectedLanguageCode } ?: supportedLanguages.first()

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${localizedString(selectedLanguage.labelRes, selectedLanguageCode)} (${selectedLanguage.nativeLabel})",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            supportedLanguages.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "${localizedString(option.labelRes, selectedLanguageCode)} (${option.nativeLabel})",
                            color = if (selectedLanguageCode == option.code) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                            fontWeight = if (selectedLanguageCode == option.code) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onLanguageSelected(option.code)
                        expanded.value = false
                    },
                    contentPadding = MenuDefaults.DropdownMenuItemContentPadding,
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

