package com.thiosin.novus.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.pager.SubredditPager
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.model.SubmissionPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val subredditInteractor: SubredditInteractor,
    private val pagingConfig: PagingConfig
) {

    suspend fun getRedditAll(): Flow<PagingData<SubmissionPreview>> = withIOContext {
        val fetcher = subredditInteractor.getRedditAllFetcher()

        Pager(pagingConfig) { SubredditPager(fetcher) }.flow
    }
}