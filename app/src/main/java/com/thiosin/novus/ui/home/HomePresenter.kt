package com.thiosin.novus.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.pager.SubredditPager
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.toLoadResultData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val subredditInteractor: SubredditInteractor,
    private val pagingConfig: PagingConfig,
) {

    suspend fun getSubreddit(
        subreddit: String,
        sort: SubmissionSort,
    ): Flow<PagingData<SubmissionPreview>> = withIOContext {
        val lister = subredditInteractor.getSubmissionsLister(
            subreddit = subreddit,
            sort = sort
        )

        Pager(pagingConfig) { SubredditPager(lister) }.flow
    }

    suspend fun getSubredditPage(
        subreddit: String,
        sort: SubmissionSort,
        count: Int = 0,
        after: String = "",
        limit: Int = 25,
    ) = withIOContext {
        val lister = subredditInteractor.getSubmissionsLister(
            subreddit = subreddit,
            sort = sort
        )

        lister.getPage(count, after, limit)?.toLoadResultData() ?: listOf()
    }
}