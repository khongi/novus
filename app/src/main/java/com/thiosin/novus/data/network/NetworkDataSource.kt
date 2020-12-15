package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse
import com.thiosin.novus.data.network.model.user.MeResponse
import com.thiosin.novus.data.network.util.apiCallBoolean
import com.thiosin.novus.data.network.util.apiCallList
import com.thiosin.novus.data.network.util.apiCallNullable
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val redditAPI: RedditAPI,
    @NetworkModule.PageSize private val pageSize: Int,
) {

    suspend fun getListing(
        subreddit: String,
        sort: String,
        count: Int,
        after: String,
    ): SubmissionListingResponse? = apiCallNullable {
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
    }

    suspend fun getUserlessSubreddits(): SubredditListingResponse? = apiCallNullable {
        redditAPI.getSubreddits()
    }

    suspend fun getUserSubreddits(): SubredditListingResponse? = apiCallNullable {
        redditAPI.getMySubreddits()
    }

    suspend fun getComments(submissionId: String): List<CResponse> = apiCallList {
        redditAPI.getComments(submissionId)
    }

    suspend fun getUserInfo(): MeResponse? = apiCallNullable {
        redditAPI.getMe()
    }

    suspend fun vote(id: String, direction: Int): Boolean = apiCallBoolean {
        redditAPI.vote(dir = direction, id = id)
    }
}