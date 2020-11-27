package com.thiosin.novus.ui.home

import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeContent(
    val listState: MutableStateFlow<List<String>>,
    val showLoading: Boolean = false
) : HomeViewState()