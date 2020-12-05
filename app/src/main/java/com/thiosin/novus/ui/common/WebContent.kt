package com.thiosin.novus.ui.common

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebContent(url: String) {
    val context = ContextAmbient.current
    val browser = remember {
        WebView(context)
    }

    browser.loadUrl(url)

    AndroidView({ browser })
}