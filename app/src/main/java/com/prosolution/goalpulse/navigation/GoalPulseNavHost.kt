package com.prosolution.goalpulse.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.key
import com.prosolution.goalpulse.localization.localizedString
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prosolution.feature.home.HomeRoute
import com.prosolution.feature.home.HomeViewModel
import com.prosolution.feature.live.LiveRoute
import com.prosolution.feature.match.MatchRoute
import com.prosolution.feature.profile.ProfileRoute
import com.prosolution.feature.standings.StandingsRoute
import com.prosolution.goalpulse.di.AppContainer

@Composable
fun GoalPulseNavHost(
    darkModeEnabled: Boolean,
    selectedLanguageCode: String,
    onDarkModeChanged: (Boolean) -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val navController = rememberNavController()
    val navBackground = MaterialTheme.colorScheme.surface
    val navIndicator = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            key(selectedLanguageCode) {
                NavigationBar(
                containerColor = navBackground,
                tonalElevation = androidx.compose.ui.unit.Dp.Hairline
            ) {
                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentDestination = navBackStackEntry?.destination
                bottomNavDestinations.forEach { destination ->
                    val destinationLabel = localizedString(destination.labelRes, selectedLanguageCode)
                    val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destinationLabel
                            )
                        },
                        label = { Text(destinationLabel) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = navIndicator,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                        )
                    )
                }
            }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestination.Home.route) {
                val viewModel: HomeViewModel = viewModel(factory = AppContainer.homeViewModelFactory())
                val state = viewModel.uiState.collectAsStateWithLifecycle()
                HomeRoute(uiState = state.value)
            }
            composable(AppDestination.Matches.route) { MatchRoute() }
            composable(AppDestination.Live.route) { LiveRoute() }
            composable(AppDestination.Standings.route) { StandingsRoute() }
            composable(AppDestination.Profile.route) {
                ProfileRoute(
                    darkModeEnabled = darkModeEnabled,
                    selectedLanguageCode = selectedLanguageCode,
                    onDarkModeChanged = onDarkModeChanged,
                    onLanguageSelected = onLanguageSelected
                )
            }
        }
    }
}
