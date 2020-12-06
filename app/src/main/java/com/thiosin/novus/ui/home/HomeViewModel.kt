package com.thiosin.novus.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load(subreddit: Subreddit? = null) = execute {
        val subreddits: List<Subreddit> = getSubreddits()

        val currentSubreddit = subreddit ?: subreddits[0]

        viewState = HomeReady(
            submissions = listOf(),
            currentSubreddit = currentSubreddit,
            subreddits = subreddits,
            loading = true,
        )

        val submissions = homePresenter.getSubredditPage(
            subreddit = currentSubreddit.name,
            sort = SubmissionSort.Hot
        )

        viewState = HomeReady(
            submissions = submissions,
            currentSubreddit = currentSubreddit,
            subreddits = subreddits
        )
    }

    fun loadNextPage() = execute {
        val oldState = viewState as? HomeReady ?: return@execute
        viewState = oldState.copy(
            submissions = oldState.submissions + homePresenter.getSubredditPage(
                subreddit = oldState.currentSubreddit.name,
                sort = SubmissionSort.Hot,
                count = oldState.submissions.size,
                after = oldState.submissions.last().fullname
            )
        )
    }

    fun showLink(url: String) = execute {
        postEvent(ShowLinkEvent(url))
    }

    private suspend fun getSubreddits(): List<Subreddit> {
        return homePresenter.getSubreddits()
    }

    data class ShowLinkEvent(val url: String) : OneShotEvent
}