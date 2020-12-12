package com.thiosin.novus.data.auth

import com.kirkbushman.auth.RedditAuth
import com.thiosin.novus.data.network.NetworkModule
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor(
    private val authInfoProvider: AuthInfoProvider,
    @NetworkModule.UserlessAuth private val userlessAuth: RedditAuth,
    @NetworkModule.InstalledAppAuth private val installedAppAuth: RedditAuth,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.v("Sending request: ${request.url}")

        val accessToken = when (authInfoProvider.loggedIn) {
            true -> {
                Timber.d("Installed App mode")
                val bearer = installedAppAuth.getSavedBearer()
                bearer.getAccessToken()
            }
            false -> {
                Timber.d("Userless mode")
                val bearer = if (userlessAuth.hasSavedBearer()) {
                    userlessAuth.getSavedBearer()
                } else {
                    userlessAuth.getTokenBearer()
                        ?: throw IllegalStateException("Could not retrieve userless bearer")
                }
                bearer.getAccessToken()
            }
        }

        val newRequest = request.newBuilder()
            .url(request.url)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}
