package com.prosolution.goalpulse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Scoreboard
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.StringRes
import com.prosolution.goalpulse.R

sealed class AppDestination(
    val route: String,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector
) {
    data object Home : AppDestination("home", R.string.nav_home, Icons.Outlined.SportsSoccer)
    data object Matches : AppDestination("matches", R.string.nav_matches, Icons.Outlined.Stadium)
    data object Live : AppDestination("live", R.string.nav_live, Icons.Outlined.LiveTv)
    data object Standings : AppDestination("standings", R.string.nav_standings, Icons.Outlined.Scoreboard)
    data object Profile : AppDestination("profile", R.string.nav_profile, Icons.Outlined.AccountCircle)
}

val bottomNavDestinations = listOf(
    AppDestination.Home,
    AppDestination.Matches,
    AppDestination.Live,
    AppDestination.Standings,
    AppDestination.Profile
)

