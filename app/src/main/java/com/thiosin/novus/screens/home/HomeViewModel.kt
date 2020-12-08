package com.thiosin.novus.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load(subreddit: Subreddit? = null) = execute {
        val readyState = viewState as? HomeReady

        val (subreddits, currentSubreddit) = if (readyState == null) {
            val subreddits: List<Subreddit> = getSubreddits()
            val currentSubreddit = subreddit ?: subreddits[0]
            Pair(subreddits, currentSubreddit)
        } else {
            Timber.d("Reusing previous state :)")
            Pair(readyState.subreddits, readyState.currentSubreddit)
        }

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

    fun switchSubreddit(subreddit: Subreddit) = execute {
        val oldState = viewState as? HomeReady ?: return@execute

        viewState = HomeReady(
            submissions = listOf(),
            currentSubreddit = subreddit,
            subreddits = oldState.subreddits,
            loading = true,
        )

        val submissions = homePresenter.getSubredditPage(
            subreddit = subreddit.name,
            sort = SubmissionSort.Hot
        )

        viewState = HomeReady(
            submissions = submissions,
            currentSubreddit = subreddit,
            subreddits = oldState.subreddits
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