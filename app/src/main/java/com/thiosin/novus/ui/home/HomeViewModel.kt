package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load() = execute {
        viewState = HomeContent(
            listFlow = homePresenter.getList(),
            showLoading = false
        )
    }

}