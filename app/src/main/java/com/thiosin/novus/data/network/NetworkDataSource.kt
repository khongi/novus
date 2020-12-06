package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.ListingResponse
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    @NetworkModule.UserlessAuth private val redditAPI: RedditAPI,
) {

    suspend fun getListing(
        subreddit: String,
        sort: String,
        count: Int,
        after: String,
        limit: Int,
    ): ListingResponse? {
        return try {
            redditAPI.getSubmissions(
                subreddit = subreddit,
                count = count,
                limit = limit,
                sort = sort,
                after = after
            )
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }
}