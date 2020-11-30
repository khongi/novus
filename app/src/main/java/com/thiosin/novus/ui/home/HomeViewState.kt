package com.thiosin.novus.ui.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeContent(
    val listFlow: Flow<PagingData<String>>,
    val showLoading: Boolean = false
) : HomeViewState()