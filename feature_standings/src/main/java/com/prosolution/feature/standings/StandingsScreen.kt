package com.prosolution.feature.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prosolution.core.data.StaticWorldCupData
import com.prosolution.core.domain.model.Standing
import com.prosolution.designsystem.GoalPulseAccentCard
import com.prosolution.designsystem.GoalPulseBackground
import com.prosolution.designsystem.GoalPulseBadge
import com.prosolution.designsystem.GoalPulseHeroCard
import com.prosolution.designsystem.GoalPulseSectionHeader

@Composable
fun StandingsRoute(modifier: Modifier = Modifier) {
    val standings = StaticWorldCupData.standings.sortedByDescending { it.points }

    GoalPulseBackground(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GoalPulseHeroCard(
                    title = "Group Standings",
                    subtitle = "A premium qualification board with point tables and ranking flow.",
                    footer = "TABLE MODE"
                )
            }

            item {
                GoalPulseSectionHeader(
                    title = "Top Contenders",
                    subtitle = "Ranked by points in this static edition"
                )
            }

            items(standings, key = { it.team.id }) { standing ->
                StandingCard(standing = standing)
            }
        }
    }
}

@Composable
private fun StandingCard(standing: Standing) {
    GoalPulseAccentCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    GoalPulseBadge(text = "GROUP ${standing.team.group}", accent = MaterialTheme.colorScheme.primary)
                    Text(
                        text = standing.team.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = standing.points.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = "P${standing.played}  W${standing.won}  D${standing.draw}  L${standing.lost}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Text(
                text = "GF ${standing.goalsFor}  GA ${standing.goalsAgainst}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
