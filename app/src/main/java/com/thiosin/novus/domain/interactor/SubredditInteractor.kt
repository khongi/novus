package com.thiosin.novus.domain.interactor

import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.helpers.AuthUserlessHelper
import javax.inject.Inject

class SubredditInteractor @Inject constructor(
    private val userlessHelper: AuthUserlessHelper
) {

    fun getRedditAllFetcher(): SubmissionsFetcher {
        val client = userlessHelper.getRedditClient()
            ?: throw IllegalStateException("Reddit client null")

        return client.subredditsClient.all()
    }
}