package com.thiosin.novus.data.network

import com.kirkbushman.auth.RedditAuth
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val redditAuth: RedditAuth
) {

    suspend fun getUserlessToken() {
        val tokenBearer = redditAuth.getTokenBearer()
        Timber.d(tokenBearer?.toString())
    }
}