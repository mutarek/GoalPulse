package com.prosolution.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldCupDao {
    @Query("SELECT * FROM schedule_cache ORDER BY fetchedAt DESC")
    fun observeSchedule(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSchedule(items: List<ScheduleEntity>)

    @Query("DELETE FROM schedule_cache")
    suspend fun clearSchedule()

    @Query("SELECT * FROM standings_cache ORDER BY fetchedAt DESC")
    fun observeStandings(): Flow<List<StandingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStandings(items: List<StandingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPrediction(prediction: PredictionEntity)

    @Query("SELECT * FROM predictions ORDER BY pointsEarned DESC")
    fun observeLeaderboard(): Flow<List<PredictionEntity>>
}

