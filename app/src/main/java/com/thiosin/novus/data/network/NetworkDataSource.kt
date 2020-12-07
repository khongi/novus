package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    @NetworkModule.UserlessAuth private val redditAPI: RedditAPI,
    @NetworkModule.PageSize private val pageSize: Int,
) {

    suspend fun getListing(
        subreddit: String,
        sort: String,
        count: Int,
        after: String,
    ): SubmissionListingResponse? {
        return try {
            if (subreddit.isBlank()) {
                redditAPI.getFrontpage(
                    count = count,
                    limit = pageSize,
                    sort = sort,
                    after = after
                )
            } else {
                redditAPI.getSubmissions(
                    subreddit = subreddit,
                    count = count,
                    limit = pageSize,
                    sort = sort,
                    after = after
                )
            }
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }

    suspend fun getUserlessSubreddits(): SubredditListingResponse? {
        return try {
            redditAPI.getSubreddits(
                count = 0,
                limit = pageSize,
                after = ""
            )
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }
}