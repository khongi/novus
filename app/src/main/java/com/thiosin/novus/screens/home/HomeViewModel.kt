package com.thiosin.novus.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeInitial) {

    fun load(subreddit: Subreddit? = null) = execute {
        val readyState = viewState as? HomeReady

        val user: User? = getUser()
        val subreddits = readyState?.subreddits ?: getSubreddits()
        val selectedSubreddit = readyState?.selectedSubreddit ?: (subreddit ?: subreddits[0])

        val submissions = homePresenter.getSubredditPage(
            subreddit = selectedSubreddit.name,
            sort = SubmissionSort.Hot
        )

        viewState = HomeReady(
            submissions = submissions,
            selectedSubreddit = selectedSubreddit,
            subreddits = subreddits,
            user = user,
        )
    }

    fun loadNextPage() = execute {
        val oldState = viewState as? HomeReady ?: return@execute

        val newSubmissions = homePresenter.getSubredditPage(
            subreddit = oldState.selectedSubreddit.name,
            sort = SubmissionSort.Hot,
            count = oldState.submissions.size,
            after = oldState.submissions.last().fullname
        )

        val submissions = oldState.submissions + newSubmissions
        submissions.forEach {
            Timber.d("${it.subreddit} ${it.author} ${it.relativeTime} ${it.votes}")
        }

        viewState = oldState.copy(
            submissions = submissions
        )
    }

    fun switchSubreddit(subreddit: Subreddit) = execute {
        val oldState = viewState as? HomeReady ?: return@execute

        viewState = oldState.copy(
            submissions = listOf(),
            selectedSubreddit = subreddit,
            loading = true,
        )

        val submissions = homePresenter.getSubredditPage(
            subreddit = subreddit.name,
            sort = SubmissionSort.Hot
        )

        viewState = oldState.copy(
            submissions = submissions,
            selectedSubreddit = subreddit,
            loading = false
        )
    }

    private suspend fun getUser(): User? {
        return homePresenter.getUser()
    }

    private suspend fun getSubreddits(): List<Subreddit> {
        return homePresenter.getSubreddits()
    }
}