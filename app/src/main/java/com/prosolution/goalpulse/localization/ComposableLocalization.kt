package com.prosolution.goalpulse.localization

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.prosolution.common.localization.LocalizationHelper

@Composable
fun localizedString(
    @StringRes stringResId: Int,
    languageCode: String
): String {
    val context = LocalContext.current
    return LocalizationHelper.getString(context, stringResId, languageCode)
}

