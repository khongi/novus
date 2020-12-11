package com.thiosin.novus.screens.home

import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User

sealed class HomeViewState

object HomeEmptyLoading : HomeViewState()

data class HomeReady(
    val submissions: List<Submission>,
    val selectedSubreddit: Subreddit,
    val subreddits: List<Subreddit>,
    val user: User? = null,
    val loading: Boolean = false,
) : HomeViewState()