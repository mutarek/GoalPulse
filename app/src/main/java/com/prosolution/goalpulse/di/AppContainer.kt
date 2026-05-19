package com.prosolution.goalpulse.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prosolution.core.domain.usecase.GetHomeFeedUseCase
import com.prosolution.feature.home.HomeViewModel

object AppContainer {

    private val repository by lazy {
        WorldCupRepositoryImpl()
    }

    private val homeFeedUseCase by lazy {
        GetHomeFeedUseCase(repository)
    }

    fun homeViewModelFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(homeFeedUseCase) as T
            }
            throw IllegalArgumentException("Unsupported ViewModel: ${modelClass.name}")
        }
    }
}
