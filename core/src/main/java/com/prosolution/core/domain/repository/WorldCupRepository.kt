package com.prosolution.core.domain.repository

import com.prosolution.common.NetworkResult
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.Standing
import kotlinx.coroutines.flow.Flow

interface WorldCupRepository {
    fun observeLiveMatches(): Flow<List<Match>>
    suspend fun refreshMatches(): NetworkResult<Unit>
    suspend fun getSchedule(day: String?): NetworkResult<List<Match>>
    suspend fun getStandings(group: String?): NetworkResult<List<Standing>>
}

