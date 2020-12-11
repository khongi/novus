package com.thiosin.novus.data.network

import com.kirkbushman.auth.RedditAuth
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor(private val redditAuth: RedditAuth) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = if (redditAuth.hasSavedBearer()) {
            redditAuth.getSavedBearer()
        } else {
            redditAuth.getTokenBearer() ?: throw IllegalStateException("Token unavailable")
        }

        Timber.v("Sending request: ${request.url}")

        val newRequest = request.newBuilder()
            .url(request.url)
            .addHeader("Authorization", "Bearer ${token.getAccessToken()}")
            .build()

        return chain.proceed(newRequest)
    }
}
