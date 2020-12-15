package com.thiosin.novus.screens.login

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.viewinterop.AndroidView
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar

@Composable
fun LoginScreen(
    authUrl: String,
    redirectUrl: String,
    onPageStart: (url: String) -> Unit,
    onAbort: () -> Unit,
) {
    val context = AmbientContext.current
    val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            if (url.startsWith(redirectUrl)) {
                view.stopLoading()
            }
            onPageStart(url)
        }
    }
    val browser = remember {
        WebView(context).apply {
            setWebViewClient(webViewClient)
        }
    }

    browser.loadUrl(authUrl)

    Scaffold(
        topBar = {
            NovusTopAppBar(
                title = "Login",
                navIcon = NavigationIcon.Close,
                onNavigationIconClick = onAbort
            )
        },
        bodyContent = {
            AndroidView({ browser })
        }
    )
}
