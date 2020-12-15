package com.thiosin.novus.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val homePresenter: HomePresenter,
) : RainbowCakeViewModel<HomeViewState>(HomeEmptyLoading) {

    fun load() = execute {
        val readyState = viewState as? HomeReady

        val user: User? = readyState?.user ?: getUser()
        if (user == null) {
            homePresenter.acquireUserlessCredentials()
        }
        val subreddits = readyState?.subreddits ?: getSubreddits()
        val selectedSubreddit = readyState?.selectedSubreddit ?: homePresenter.getDefaultSubreddit()

        val submissions = homePresenter.getSubredditPage(
            subreddit = selectedSubreddit.queryName
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
            subreddit = oldState.selectedSubreddit.queryName,
            count = oldState.submissions.size,
            after = oldState.submissions.last().fullname
        )

        val submissions = oldState.submissions + newSubmissions
        submissions.forEach {
            Timber.v("${it.subreddit} ${it.author} ${it.relativeTime} ${it.votes}")
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
            subreddit = subreddit.queryName,
        )

        viewState = oldState.copy(
            submissions = submissions,
            selectedSubreddit = subreddit,
            loading = false
        )
    }

    fun startLoading() = execute {
        viewState = HomeEmptyLoading
    }

    fun switchToUserlessMode() = execute {
        val oldState = viewState as? HomeReady

        viewState = HomeReady(
            submissions = oldState?.submissions ?: emptyList(),
            selectedSubreddit = oldState?.selectedSubreddit ?: homePresenter.getDefaultSubreddit(),
            subreddits = oldState?.subreddits ?: emptyList(),
            loading = true
        )

        homePresenter.logout()
        homePresenter.acquireUserlessCredentials()

        val subreddits = getSubreddits()
        val selectedSubreddit = subreddits[0]
        val submissions = homePresenter.getSubredditPage(
            subreddit = selectedSubreddit.queryName
        )

        viewState = HomeReady(
            submissions = submissions,
            selectedSubreddit = selectedSubreddit,
            subreddits = subreddits,
        )
    }

    fun vote(fullname: String, liked: Boolean?) = execute {
        val oldState = viewState as? HomeReady ?: return@execute
        viewState = oldState.copy(voting = true)

        if (homePresenter.vote(fullname, liked)) {
            oldState.submissions.first { it.fullname == fullname }.liked = liked
        }

        viewState = oldState.copy(voting = false)
    }

    private suspend fun getUser(): User? {
        return homePresenter.getUser()
    }

    private suspend fun getSubreddits(): List<Subreddit> {
        return homePresenter.getSubreddits()
    }
}
