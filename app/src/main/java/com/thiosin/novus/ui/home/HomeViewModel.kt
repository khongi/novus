package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionSort

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load(subreddit: String) = execute {
        viewState = HomeReady(
            submissions = listOf(),
            subreddit = subreddit,
            loading = true
        )
        viewState = HomeReady(
            submissions = homePresenter.getSubredditPage(
                subreddit = subreddit,
                sort = SubmissionSort.Hot
            ),
            subreddit = subreddit,
        )
    }

    fun loadNextPage() = execute {
        val oldState = viewState as? HomeReady ?: return@execute
        viewState = oldState.copy(
            submissions = oldState.submissions + homePresenter.getSubredditPage(
                subreddit = oldState.subreddit,
                sort = SubmissionSort.Hot,
                count = oldState.submissions.size,
                after = oldState.submissions.last().fullname
            )
        )
    }

    fun showLink(url: String) = execute {
        postEvent(ShowLinkEvent(url))
    }

    data class ShowLinkEvent(val url: String) : OneShotEvent
}