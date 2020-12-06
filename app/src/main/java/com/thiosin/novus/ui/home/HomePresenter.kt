package com.thiosin.novus.ui.home

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.toLoadResultData
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val subredditInteractor: SubredditInteractor,
) {

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