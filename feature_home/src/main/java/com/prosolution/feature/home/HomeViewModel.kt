package com.prosolution.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prosolution.common.NetworkResult
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.core.domain.usecase.GetHomeFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeFeedUseCase: GetHomeFeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = getHomeFeedUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        liveMatches = result.data.filter { it.status == MatchStatus.LIVE },
                        upcomingMatches = result.data.filter { it.status == MatchStatus.UPCOMING }
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                NetworkResult.Loading -> Unit
            }
        }
    }
}
