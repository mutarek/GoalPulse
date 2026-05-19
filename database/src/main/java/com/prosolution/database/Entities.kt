package com.prosolution.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_teams")
data class FavoriteTeamEntity(
    @PrimaryKey val teamId: String,
    val name: String,
    val crestUrl: String
)

@Entity(tableName = "schedule_cache")
data class ScheduleEntity(
    @PrimaryKey val matchId: String,
    val payload: String,
    val fetchedAt: Long
)

@Entity(tableName = "standings_cache")
data class StandingEntity(
    @PrimaryKey val id: String,
    val payload: String,
    val fetchedAt: Long
)

@Entity(tableName = "predictions")
data class PredictionEntity(
    @PrimaryKey val id: String,
    val matchId: String,
    val homeScore: Int,
    val awayScore: Int,
    val pointsEarned: Int
)

