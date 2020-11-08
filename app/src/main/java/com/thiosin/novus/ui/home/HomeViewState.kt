package com.thiosin.novus.ui.home

sealed class HomeViewState

object Initial : HomeViewState()

object Loading : HomeViewState()

data class HomeReady(val data: String) : HomeViewState()