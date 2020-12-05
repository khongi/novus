package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionSort

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load(subreddit: String) = execute {
        viewState = HomeReady(
            listState = homePresenter.getSubreddit(
                subreddit = subreddit,
                sort = SubmissionSort.Hot
            ),
            subreddit = subreddit,
        )
    }
}