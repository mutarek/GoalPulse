package com.prosolution.feature.match

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prosolution.core.data.StaticWorldCupData
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.designsystem.GoalPulseBackground
import com.prosolution.designsystem.GoalPulseBadge
import com.prosolution.designsystem.GoalPulseDividerLabel
import com.prosolution.designsystem.GoalPulseHeroCard
import com.prosolution.designsystem.GoalPulseSectionHeader
import com.prosolution.designsystem.GoalPulseStatCard

private val groupLabels = listOf("All", "A", "B", "C", "D", "E", "F", "G", "H")

@Composable
fun MatchRoute(modifier: Modifier = Modifier) {
    var selectedGroup by remember { mutableStateOf("All") }

    val filteredMatches = remember(selectedGroup) {
        if (selectedGroup == "All") StaticWorldCupData.matches
        else StaticWorldCupData.matches.filter { it.homeTeam.group == selectedGroup }
    }

    val groupedMatches = filteredMatches
        .sortedBy { it.startTimeUtc }
        .groupBy { it.startTimeUtc.substring(0, 10) }

    GoalPulseBackground(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero card
            item {
                GoalPulseHeroCard(
                    title = "FIFA World Cup 2026™",
                    subtitle = "Canada · Mexico · USA — Jun 11 – Jul 19, 2026",
                    footer = "⚽  MATCH SCHEDULE"
                )
            }

            // Stats row
            item {
                val live     = StaticWorldCupData.matches.count { it.status == MatchStatus.LIVE }
                val finished = StaticWorldCupData.matches.count { it.status == MatchStatus.FINISHED }
                val upcoming = StaticWorldCupData.matches.count { it.status == MatchStatus.UPCOMING }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GoalPulseStatCard(label = "🔴 Live",     value = live.toString(),     accent = MaterialTheme.colorScheme.tertiary,  modifier = Modifier.width(110.dp))
                    GoalPulseStatCard(label = "✅ Finished", value = finished.toString(), accent = MaterialTheme.colorScheme.primary,   modifier = Modifier.width(110.dp))
                    GoalPulseStatCard(label = "📅 Upcoming", value = upcoming.toString(), accent = MaterialTheme.colorScheme.secondary, modifier = Modifier.width(110.dp))
                }
            }

            // Group filter chips
            item {
                GoalPulseSectionHeader(
                    title = "Filter by Group",
                    subtitle = "Tap a group to focus on its fixtures"
                )
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(groupLabels) { label ->
                        FilterChip(
                            selected = selectedGroup == label,
                            onClick = { selectedGroup = label },
                            label = {
                                Text(
                                    text = if (label == "All") "All" else "Group $label",
                                    fontWeight = if (selectedGroup == label) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color(0xFF0B1020)
                            )
                        )
                    }
                }
            }

            // Matches grouped by date
            if (groupedMatches.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No fixtures found", color = Color.White.copy(alpha = 0.5f))
                    }
                }
            } else {
                groupedMatches.forEach { (date, dayMatches) ->
                    item(key = "date_$date") {
                        GoalPulseDividerLabel(text = formatDate(date))
                    }
                    items(dayMatches, key = { it.id }) { match ->
                        FlagMatchCard(match = match)
                    }
                }
            }
        }
    }
}

// ─── Date formatter ──────────────────────────────────────────────────────────

private fun formatDate(iso: String): String {
    return try {
        val parts = iso.split("-")
        val year  = parts[0]
        val month = listOf("","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")[parts[1].toInt()]
        val day   = parts[2].trimStart('0')
        "$month $day, $year"
    } catch (_: Exception) { iso }
}

// ─── Match card with flags ────────────────────────────────────────────────────

@Composable
private fun FlagMatchCard(match: Match) {
    val statusColor = when (match.status) {
        MatchStatus.LIVE     -> MaterialTheme.colorScheme.tertiary
        MatchStatus.FINISHED -> MaterialTheme.colorScheme.primary
        MatchStatus.UPCOMING -> MaterialTheme.colorScheme.secondary
    }
    val badgeText = when (match.status) {
        MatchStatus.LIVE     -> "🔴  LIVE  ${match.minute}'"
        MatchStatus.FINISHED -> "✅  FINAL"
        MatchStatus.UPCOMING -> "📅  UPCOMING"
    }
    val cardGradient = Brush.linearGradient(
        when (match.status) {
            MatchStatus.LIVE     -> listOf(Color(0xFF1A1020), Color(0xFF200A0A), statusColor.copy(0.12f))
            MatchStatus.FINISHED -> listOf(Color(0xFF0E1A30), Color(0xFF0B1520))
            MatchStatus.UPCOMING -> listOf(Color(0xFF0D1425), Color(0xFF111A33))
        }
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(cardGradient, RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // Badge row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GoalPulseBadge(text = badgeText, accent = statusColor)
                    Text(
                        text = "Group ${match.homeTeam.group}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Teams + score row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Home team
                    TeamFlagColumn(
                        flag      = match.homeTeam.flag,
                        shortCode = match.homeTeam.shortCode,
                        name      = match.homeTeam.name,
                        modifier  = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    // Score / VS
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        if (match.status == MatchStatus.UPCOMING) {
                            Text(
                                text = "VS",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = Color.White.copy(alpha = 0.35f)
                            )
                            Text(
                                text = utcToLocal(match.startTimeUtc),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                text = "${match.homeScore}  –  ${match.awayScore}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = if (match.status == MatchStatus.LIVE)
                                    MaterialTheme.colorScheme.tertiary
                                else
                                    Color.White
                            )
                            if (match.status == MatchStatus.LIVE) {
                                Text(
                                    text = "${match.minute}'",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Away team
                    TeamFlagColumn(
                        flag      = match.awayTeam.flag,
                        shortCode = match.awayTeam.shortCode,
                        name      = match.awayTeam.name,
                        modifier  = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }

                // Stadium row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🏟️", fontSize = 12.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = match.stadium,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamFlagColumn(
    flag: String,
    shortCode: String,
    name: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = flag,
            fontSize = 42.sp,
            modifier = Modifier.size(52.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = shortCode,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = textAlign
        )
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.65f),
            textAlign = textAlign,
            maxLines = 1
        )
    }
}

/** Formats "2026-06-14T18:00:00Z" → "18:00 UTC" */
private fun utcToLocal(iso: String): String {
    return try {
        val hour24 = iso.substring(11, 13).toInt()
        val minute = iso.substring(14, 16)
        val period = if (hour24 >= 12) "PM" else "AM"
        val hour12 = when (hour24 % 12) {
            0 -> 12
            else -> hour24 % 12
        }
        "$hour12:$minute $period UTC"
    } catch (_: Exception) { iso }
}
