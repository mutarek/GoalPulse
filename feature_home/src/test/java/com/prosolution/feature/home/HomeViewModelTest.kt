package com.prosolution.feature.home

import app.cash.turbine.test
import com.prosolution.common.NetworkResult
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.core.domain.model.Standing
import com.prosolution.core.domain.model.Team
import com.prosolution.core.domain.repository.WorldCupRepository
import com.prosolution.core.domain.usecase.GetHomeFeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh emits live and upcoming matches`() = runTest {
        val repository = object : WorldCupRepository {
            override fun observeLiveMatches() = flowOf(emptyList<Match>())

            override suspend fun refreshMatches() = NetworkResult.Success(Unit)

            override suspend fun getSchedule(day: String?) = NetworkResult.Success(
                listOf(
                    sampleMatch(status = MatchStatus.LIVE),
                    sampleMatch(id = "upcoming", status = MatchStatus.UPCOMING)
                )
            )

            override suspend fun getStandings(group: String?): NetworkResult<List<Standing>> =
                NetworkResult.Success(emptyList())
        }
        val viewModel = HomeViewModel(GetHomeFeedUseCase(repository))

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val latest = awaitItem()
            assertEquals(1, latest.liveMatches.size)
            assertEquals(1, latest.upcomingMatches.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun sampleMatch(id: String = "live", status: MatchStatus): Match = Match(
        id = id,
        homeTeam = Team("h", "Home", "H", "", "A"),
        awayTeam = Team("a", "Away", "A", "", "A"),
        startTimeUtc = "2026-01-01T12:00:00Z",
        stadium = "Test",
        homeScore = 0,
        awayScore = 0,
        minute = 10,
        status = status
    )
}

