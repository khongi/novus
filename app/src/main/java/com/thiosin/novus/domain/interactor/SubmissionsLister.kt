package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.data.network.model.ListingResponse
import com.thiosin.novus.domain.model.SubmissionSort

class SubmissionsLister(
    private val subreddit: String,
    private val sort: SubmissionSort,
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getPage(count: Int, after: String, limit: Int): ListingResponse? {
        return networkDataSource.getListing(
            subreddit = subreddit,
            sort = sort.getApiValue(),
            count = count,
            after = after,
            limit = limit
        )
    }
}