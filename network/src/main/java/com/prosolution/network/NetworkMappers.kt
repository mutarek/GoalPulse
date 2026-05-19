package com.prosolution.network

import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.core.domain.model.Standing
import com.prosolution.core.domain.model.Team

fun MatchDto.toDomain(): Match = Match(
    id = id,
    homeTeam = homeTeam.toDomain(),
    awayTeam = awayTeam.toDomain(),
    startTimeUtc = startTimeUtc,
    stadium = stadium,
    homeScore = homeScore,
    awayScore = awayScore,
    minute = minute,
    status = status.toMatchStatus()
)

fun StandingDto.toDomain(): Standing = Standing(
    team = team.toDomain(),
    played = played,
    won = won,
    draw = draw,
    lost = lost,
    goalsFor = goalsFor,
    goalsAgainst = goalsAgainst,
    points = points
)

private fun TeamDto.toDomain(): Team = Team(
    id = id,
    name = name,
    shortCode = shortCode,
    crestUrl = crestUrl,
    group = group
)

private fun String.toMatchStatus(): MatchStatus = when (uppercase()) {
    "LIVE" -> MatchStatus.LIVE
    "FINISHED" -> MatchStatus.FINISHED
    else -> MatchStatus.UPCOMING
}

