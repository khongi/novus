package com.thiosin.novus.domain.interactor

import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.models.TokenBearer
import com.thiosin.novus.BuildConfig
import com.thiosin.novus.data.auth.AuthInfoProvider
import com.thiosin.novus.data.network.NetworkModule
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    @NetworkModule.UserlessAuth private val userlessRedditAuth: RedditAuth,
    @NetworkModule.InstalledAppAuth private val userRedditAuth: RedditAuth,
    private val authInfoProvider: AuthInfoProvider,
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

    fun acquireUserToken(url: String): TokenBearer? {
        return userRedditAuth.getTokenBearer(url)
    }

    fun getRedirectUrl() = BuildConfig.REDIRECT_URL

    fun logout() {
        authInfoProvider.clearAll()
    }

    fun setLoggedInStatus(status: Boolean) {
        authInfoProvider.loggedIn = status
    }
}