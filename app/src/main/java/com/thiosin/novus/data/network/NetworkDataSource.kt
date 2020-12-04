package com.thiosin.novus.data.network

import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    @NetworkModule.UserlessAuth private val redditAPI: RedditAPI
) {

    suspend fun getAll() {
        try {
            val response = redditAPI.getSubmissions(
                subreddit = "all",
                count = 0,
                limit = 25,
                sort = "hot",
                after = ""
            )
        } catch (t: Throwable) {
            Timber.e(t)
        }
    }
}