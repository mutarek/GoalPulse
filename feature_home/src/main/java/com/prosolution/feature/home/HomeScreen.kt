package com.prosolution.feature.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.Team
import com.prosolution.designsystem.GoalPulseAccentCard
import com.prosolution.designsystem.GoalPulseBadge
import com.prosolution.designsystem.GoalPulseBackground
import com.prosolution.designsystem.GoalPulseHeroCard
import com.prosolution.designsystem.GoalPulseSectionHeader
import com.prosolution.designsystem.GoalPulseStatCard

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    GoalPulseBackground(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@GoalPulseBackground
        }

        val featuredMatch = uiState.liveMatches.firstOrNull() ?: uiState.upcomingMatches.firstOrNull()
        val totalMatches = uiState.liveMatches.size + uiState.upcomingMatches.size
        val teamsCount = (uiState.liveMatches + uiState.upcomingMatches)
            .flatMap { listOf(it.homeTeam.id, it.awayTeam.id) }
            .distinct()
            .size

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GoalPulseHeroCard(
                    title = featuredMatch?.let {
                        "${it.homeTeam.flag} ${it.homeTeam.shortCode} vs ${it.awayTeam.shortCode} ${it.awayTeam.flag}"
                    } ?: "World Cup Live Dashboard",
                    subtitle = featuredMatch?.let {
                        "${it.stadium} • ${formatKickoffTime(it.startTimeUtc)}"
                    } ?: "Track live matches, upcoming fixtures, and tournament momentum in one glance.",
                    footer = if (uiState.liveMatches.isNotEmpty()) "⚽ LIVE NOW" else "UPCOMING FIXTURES"
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GoalPulseStatCard(
                        label = "Total",
                        value = totalMatches.toString(),
                        modifier = Modifier.width(110.dp)
                    )
                    GoalPulseStatCard(
                        label = "Live",
                        value = uiState.liveMatches.size.toString(),
                        accent = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.width(110.dp)
                    )
                    GoalPulseStatCard(
                        label = "Teams",
                        value = teamsCount.toString(),
                        accent = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(110.dp)
                    )
                }
            }

            item {
                GoalPulseSectionHeader(
                    title = "🔴 Live Matches",
                    subtitle = "Active matches with live score and minute"
                )
            }

            if (uiState.liveMatches.isEmpty()) {
                item {
                    GoalPulseAccentCard {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            GoalPulseBadge(text = "NO LIVE GAMES")
                            Text(
                                text = "No live match right now. Check upcoming fixtures below.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else {
                items(uiState.liveMatches, key = { it.id }) { match ->
                    HomeMatchCard(match = match, live = true)
                }
            }

            item {
                GoalPulseSectionHeader(
                    title = "📅 Upcoming Matches",
                    subtitle = "The next kickoffs in this tournament edition"
                )
            }

            if (uiState.upcomingMatches.isEmpty()) {
                item {
                    GoalPulseAccentCard {
                        Text(
                            text = "No upcoming matches to display.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            } else {
                items(uiState.upcomingMatches, key = { it.id }) { match ->
                    HomeMatchCard(match = match, live = false)
                }
            }
        }
    }
}

@Composable
private fun HomeMatchCard(match: Match, live: Boolean) {
    GoalPulseAccentCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            GoalPulseBadge(
                text = if (live) "LIVE ${match.minute}'" else "UPCOMING",
                accent = if (live) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamMiniBlock(
                    team = match.homeTeam,
                    modifier = Modifier.weight(1f),
                    alignment = Alignment.Start,
                    textAlign = TextAlign.Start
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (live) {
                        Text(
                            text = "${match.homeScore} - ${match.awayScore}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                        )
                    }
                }
                TeamMiniBlock(
                    team = match.awayTeam,
                    modifier = Modifier.weight(1f),
                    alignment = Alignment.End,
                    textAlign = TextAlign.End
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.stadium,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatKickoffTime(match.startTimeUtc),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TeamMiniBlock(
    team: Team,
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal,
    textAlign: TextAlign
) {
    Column(
        modifier = modifier,
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = team.flag,
            fontSize = 24.sp
        )
        Text(
            text = team.shortCode,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign
        )
        Text(
            text = team.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.82f),
            textAlign = textAlign,
            maxLines = 1
        )
    }
}

private fun formatKickoffTime(iso: String): String {
    return try {
        val hour24 = iso.substring(11, 13).toInt()
        val minute = iso.substring(14, 16)
        val period = if (hour24 >= 12) "PM" else "AM"
        val hour12 = when (hour24 % 12) {
            0 -> 12
            else -> hour24 % 12
        }
        "$hour12:$minute $period UTC"
    } catch (_: Exception) {
        iso
    }
}

