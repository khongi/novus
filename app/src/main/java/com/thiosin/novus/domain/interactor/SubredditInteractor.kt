package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.SubmissionSort
import javax.inject.Inject

class SubredditInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {

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