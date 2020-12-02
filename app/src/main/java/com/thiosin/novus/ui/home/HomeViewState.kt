package com.thiosin.novus.ui.home

import androidx.paging.PagingData
import com.thiosin.novus.domain.model.SubmissionPreview
import kotlinx.coroutines.flow.Flow

sealed class HomeViewState

object HomeInitial : HomeViewState()

data class HomeContent(
    val listFlow: Flow<PagingData<SubmissionPreview>>,
    val title: String,
    val showLoading: Boolean = false
) : HomeViewState()