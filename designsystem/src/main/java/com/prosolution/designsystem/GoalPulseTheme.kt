package com.prosolution.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF00E5FF),
    secondary = Color(0xFFFFCC00),
    tertiary = Color(0xFFFF3B30),
    background = Color(0xFF0B1020),
    surface = Color(0xFF111A33)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF007C91),
    secondary = Color(0xFF9D7A00),
    tertiary = Color(0xFFB71C1C),
    background = Color(0xFFF5F8FF),
    surface = Color(0xFFFFFFFF)
)

@Composable
fun GoalPulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}

