package com.thiosin.novus.ui.home

import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeViewState

object Initial : HomeViewState()

data class Content(
    val listState: MutableStateFlow<List<String>>,
    val showLoading: Boolean = false
) : HomeViewState()