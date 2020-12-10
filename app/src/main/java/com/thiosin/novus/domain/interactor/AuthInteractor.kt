package com.thiosin.novus.domain.interactor

import com.kirkbushman.auth.RedditAuth
import com.thiosin.novus.BuildConfig
import com.thiosin.novus.data.network.NetworkModule
import com.thiosin.novus.domain.model.User
import timber.log.Timber
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

    fun getUser(): User? {
//        userlessRedditAuth.getTokenBearer()

        val authType = userRedditAuth.getAuthType()
        Timber.d("AuthType: $authType")

        val userToken = userRedditAuth.getSavedBearer().getAuthHeaderStr()
        Timber.d("User token: $userToken")

        val userlessToken = userlessRedditAuth.getSavedBearer().getAuthHeaderStr()
        Timber.d("Userless token: $userlessToken")

        return null
    }

    fun getRedirectUrl() = BuildConfig.REDIRECT_URL
}