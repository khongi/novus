package com.thiosin.novus.screens.home

import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.Subreddit

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeReady(
    val submissions: List<Submission>,
    val selectedSubreddit: Subreddit,
    val subreddits: List<Subreddit>,
    val loading: Boolean = false,
) : HomeViewState()