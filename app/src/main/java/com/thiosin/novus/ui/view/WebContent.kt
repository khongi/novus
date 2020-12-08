package com.thiosin.novus.ui.view

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.viewinterop.AndroidView
import timber.log.Timber

@Composable
fun WebContent(sourceUrl: String) {
    val context = ContextAmbient.current
    val isLoading = remember { mutableStateOf(true) }

    val webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            isLoading.value = false
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?,
        ): Boolean {
            val requestUrl = request?.url?.toString()
            if (requestUrl == null) {
                Timber.e("Request URL was null")
                return true
            }
            Timber.v("Loading URL: $requestUrl")
            view?.loadUrl(requestUrl)
            return false
        }
    }

    val browser = remember {
        WebView(context).apply {
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            setWebViewClient(webViewClient)
        }
    }

    browser.loadUrl(sourceUrl)

    Box {
        AndroidView({ browser })
        if (isLoading.value) {
            LoadingScreen()
        }
    }
}