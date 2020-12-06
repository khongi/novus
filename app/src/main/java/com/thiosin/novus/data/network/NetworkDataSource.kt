package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.domain.model.Subreddit
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
    ): SubmissionListingResponse? {
        return try {
            if (subreddit.isBlank()) {
                redditAPI.getFrontpage(
                    count = count,
                    limit = limit,
                    sort = sort,
                    after = after
                )
            } else {
                redditAPI.getSubmissions(
                    subreddit = subreddit,
                    count = count,
                    limit = limit,
                    sort = sort,
                    after = after
                )
            }
        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }

    fun getUserlessSubreddits(): List<Subreddit> {
        return listOf(
            "",
            "All",
            "Popular",
            "funny",
            "worldnews",
            "AskReddit",
            "videos",
            "Music"
        ).map {
            Subreddit(
                name = it,
                displayName = if (it.isBlank()) "Frontpage" else "/r/$it",
                icon = ""
            )
        }
    }
}