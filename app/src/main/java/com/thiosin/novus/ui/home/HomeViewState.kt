package com.thiosin.novus.ui.home

import com.thiosin.novus.domain.model.SubmissionPreview

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeReady(
    val submissions: List<SubmissionPreview>,
    val subreddit: String,
    val loading: Boolean = false,
) : HomeViewState()