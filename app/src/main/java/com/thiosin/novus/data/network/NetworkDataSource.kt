package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse
import com.thiosin.novus.data.network.model.user.MeResponse
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    // TODO check if the same token is returned with userless RedditAuth
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
            redditAPI.getSubreddits()
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }

    suspend fun getUserSubreddits(): SubredditListingResponse? {
        return try {
            redditAPI.getMySubreddits()
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }

    suspend fun getComments(submissionId: String): List<CResponse> {
        return try {
            redditAPI.getComments(submissionId)
        } catch (t: Throwable) {
            Timber.e(t)
            emptyList()
        }
    }

    suspend fun getUserInfo(): MeResponse? {
        return try {
            redditAPI.getMe()
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }
}