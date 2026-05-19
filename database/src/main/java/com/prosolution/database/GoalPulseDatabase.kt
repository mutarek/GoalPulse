package com.prosolution.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteTeamEntity::class,
        ScheduleEntity::class,
        StandingEntity::class,
        PredictionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GoalPulseDatabase : RoomDatabase() {
    abstract fun worldCupDao(): WorldCupDao
}

