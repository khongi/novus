package com.thiosin.novus.domain.interactor

import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.helpers.AuthUserlessHelper
import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.SubmissionSort
import javax.inject.Inject

class SubredditInteractor @Inject constructor(
    private val userlessHelper: AuthUserlessHelper,
    private val networkDataSource: NetworkDataSource
) {

    fun getRedditAllFetcher(): SubmissionsFetcher {
        val client = userlessHelper.getRedditClient()
            ?: throw IllegalStateException("Reddit client null")

        return client.subredditsClient.all()
    }

    fun getSubmissionsLister(
        subreddit: String,
        sort: SubmissionSort = SubmissionSort.Hot
    ): SubmissionsLister {
        return SubmissionsLister(
            subreddit = subreddit,
            sort = sort,
            networkDataSource = networkDataSource
        )
    }
}