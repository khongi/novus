package com.thiosin.novus.ui.home

import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.domain.model.Subreddit

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeReady(
    val submissions: List<SubmissionPreview>,
    val currentSubreddit: Subreddit,
    val subreddits: List<Subreddit>,
    val loading: Boolean = false,
) : HomeViewState()