package com.thiosin.novus.domain.interactor

import com.kirkbushman.auth.RedditAuth
import com.thiosin.novus.data.network.NetworkModule
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    @NetworkModule.UserlessAuth private val userlessRedditAuth: RedditAuth,
    @NetworkModule.UserAuth private val userRedditAuth: RedditAuth,
) {

    fun acquireUserlessToken() {
        userlessRedditAuth.getTokenBearer()
    }

    fun getUserAuthUrl(): String {
        return userRedditAuth.provideAuthorizeUrl()
    }

    fun isUserRedirectUrl(url: String): Boolean {
        return userRedditAuth.isRedirectedUrl(url)
    }

    fun acquireUserToken(url: String) {
        userRedditAuth.getTokenBearer(url)
    }
}