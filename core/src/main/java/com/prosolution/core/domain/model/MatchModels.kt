package com.prosolution.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: String,
    val name: String,
    val shortCode: String,
    val crestUrl: String,
    val group: String,
    val flag: String = ""
)

@Serializable
data class Match(
    val id: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val startTimeUtc: String,
    val stadium: String,
    val homeScore: Int,
    val awayScore: Int,
    val minute: Int,
    val status: MatchStatus
)

enum class MatchStatus {
    UPCOMING,
    LIVE,
    FINISHED
}

@Serializable
data class Standing(
    val team: Team,
    val played: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val points: Int
)


