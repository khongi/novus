package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.data.network.NetworkModule
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import javax.inject.Inject

class SubredditInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @NetworkModule.PageSize private val pageSize: Int,
) {

    fun getSubmissionsLister(
        subreddit: String,
        sort: SubmissionSort = SubmissionSort.Hot,
    ): SubmissionsLister {
        return SubmissionsLister(
            subreddit = subreddit,
            sort = sort,
            networkDataSource = networkDataSource,
            pageSize = pageSize
        )
    }

    fun getSubreddits(): List<Subreddit> {
        return networkDataSource.getUserlessSubreddits()
    }
}