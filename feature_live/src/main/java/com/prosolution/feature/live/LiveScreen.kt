package com.prosolution.feature.live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prosolution.core.data.StaticWorldCupData
import com.prosolution.designsystem.GoalPulseAccentCard
import com.prosolution.designsystem.GoalPulseBackground
import com.prosolution.designsystem.GoalPulseBadge
import com.prosolution.designsystem.GoalPulseHeroCard
import com.prosolution.designsystem.GoalPulseSectionHeader
import com.prosolution.designsystem.GoalPulseStatCard

@Composable
fun LiveRoute(modifier: Modifier = Modifier) {
    val liveMatches = StaticWorldCupData.liveMatches()
    val latestMatch = liveMatches.firstOrNull()

    GoalPulseBackground(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GoalPulseHeroCard(
                    title = latestMatch?.let { "${it.homeTeam.name}  vs  ${it.awayTeam.name}" } ?: "Live Match Center",
                    subtitle = latestMatch?.let {
                        "${it.stadium}  •  Minute ${it.minute}"
                    } ?: "No live match right now. Come back when a match kicks off.",
                    footer = if (liveMatches.isNotEmpty()) "🔴 ON AIR" else "⏸ STANDBY"
                )
            }

            item {
                GoalPulseStatCard(
                    label = "Live Right Now",
                    value = liveMatches.size.toString(),
                    accent = MaterialTheme.colorScheme.tertiary
                )
            }

            item {
                GoalPulseSectionHeader(
                    title = "🔴 Live Scoreboard",
                    subtitle = "Realtime scores, minute, and venue"
                )
            }

            if (latestMatch != null) {
                items(liveMatches) { match ->
                    GoalPulseAccentCard {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            GoalPulseBadge(text = "LIVE ${match.minute}'", accent = MaterialTheme.colorScheme.tertiary)
                            Text(
                                text = "${match.homeTeam.name}  ${match.homeScore}  -  ${match.awayScore}  ${match.awayTeam.name}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = match.stadium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Possession • Cards • Substitutions • Commentary — ready for WebSocket integration.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                            )
                        }
                    }
                }
            } else {
                item {
                    GoalPulseAccentCard {
                        Text(
                            text = "No live fixtures at the moment. The live tile will auto-populate when a match kicks off.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
