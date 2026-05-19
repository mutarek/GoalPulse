package com.prosolution.goalpulse.di

import com.prosolution.common.NetworkResult
import com.prosolution.core.data.StaticWorldCupData
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.core.domain.model.Standing
import com.prosolution.core.domain.repository.WorldCupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorldCupRepositoryImpl : WorldCupRepository {

    private val liveMatches = MutableStateFlow(StaticWorldCupData.liveMatches())

    override fun observeLiveMatches(): Flow<List<Match>> = liveMatches.asStateFlow()

    override suspend fun refreshMatches(): NetworkResult<Unit> {
        liveMatches.value = StaticWorldCupData.liveMatches()
        return NetworkResult.Success(Unit)
    }

    override suspend fun getSchedule(day: String?): NetworkResult<List<Match>> =
        NetworkResult.Success(StaticWorldCupData.scheduleForDay(day))

    override suspend fun getStandings(group: String?): NetworkResult<List<Standing>> {
        val standings = if (group.isNullOrBlank()) {
            StaticWorldCupData.standings
        } else {
            StaticWorldCupData.standings.filter { it.team.group.equals(group, ignoreCase = true) }
        }
        return NetworkResult.Success(standings)
    }
}
