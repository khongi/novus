package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load() = execute {
        Timber.d("LOADING")
        homePresenter.test()

//        viewState = HomeContent(
//            listFlow = homePresenter.getRedditAll(),
//            title = "All", // TODO get title
//            showLoading = false
//        )
    }
}