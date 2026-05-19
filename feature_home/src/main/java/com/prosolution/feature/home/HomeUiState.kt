package com.prosolution.feature.home

import com.prosolution.core.domain.model.Match

data class HomeUiState(
    val isLoading: Boolean = true,
    val liveMatches: List<Match> = emptyList(),
    val upcomingMatches: List<Match> = emptyList(),
    val error: String? = null
)

