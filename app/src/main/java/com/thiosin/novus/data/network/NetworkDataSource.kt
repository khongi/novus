package com.thiosin.novus.data.network

import com.kirkbushman.araw.helpers.AuthUserlessHelper
import com.kirkbushman.auth.RedditAuth
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val redditAuth: RedditAuth,
    private val userlessHelper: AuthUserlessHelper
) {

    suspend fun getUserlessToken() {
        val tokenBearer = redditAuth.getTokenBearer()
        Timber.d(tokenBearer?.getAccessToken())
        Timber.d("Should login: ${userlessHelper.shouldLogin()}")
    }
}