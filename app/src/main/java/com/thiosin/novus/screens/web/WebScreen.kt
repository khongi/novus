package com.thiosin.novus.screens.web

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.viewinterop.AndroidView
import com.thiosin.novus.ui.view.LoadingScreen
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar
import timber.log.Timber

@Composable
fun WebScreen(sourceUrl: String, onClose: () -> Unit) {
    val context = AmbientContext.current
    val isLoading = remember { mutableStateOf(true) }
    val host = remember { mutableStateOf("") }

    val webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            isLoading.value = false
            host.value = Uri.parse(view?.url.toString()).host ?: ""
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

    Scaffold(
        topBar = {
            NovusTopAppBar(
                title = host.value,
                navIcon = NavigationIcon.Close,
                onNavigationIconClick = onClose
            )
        },
        bodyContent = {
            Box {
                AndroidView({ browser })
                if (isLoading.value) {
                    LoadingScreen()
                }
            }
        }
    )
}