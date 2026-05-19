package com.prosolution.core.domain.usecase

import com.prosolution.common.NetworkResult
import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.repository.WorldCupRepository

class GetHomeFeedUseCase(
    private val repository: WorldCupRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Match>> = repository.getSchedule(day = null)
}
