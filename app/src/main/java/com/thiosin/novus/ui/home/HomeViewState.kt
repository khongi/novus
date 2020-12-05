package com.thiosin.novus.ui.home

import androidx.paging.PagingData
import com.thiosin.novus.domain.model.SubmissionPreview
import kotlinx.coroutines.flow.Flow

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeReady(
    val listState: Flow<PagingData<SubmissionPreview>>,
    val subreddit: String,
) : HomeViewState()