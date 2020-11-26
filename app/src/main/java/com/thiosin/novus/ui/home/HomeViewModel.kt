package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter
) : RainbowCakeViewModel<HomeViewState>(Initial) {

    fun load() = execute {
        viewState = Content(
            listState = MutableStateFlow(homePresenter.getData()),
            showLoading = false
        )
    }

    fun loadNext() = execute {
        val oldState = viewState as? Content ?: return@execute
        viewState = oldState.copy(showLoading = true)

        oldState.listState.value = oldState.listState.value + homePresenter.getNextData()
        viewState = oldState.copy(showLoading = false)
    }
}