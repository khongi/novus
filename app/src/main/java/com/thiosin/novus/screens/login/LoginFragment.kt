package com.thiosin.novus.screens.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NovusTheme { }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.startLogin()
    }

    override fun render(viewState: LoginViewState) {
        (view as ComposeView).setContent {
            NovusTheme {
                when (viewState) {
                    is LoginInitial -> Unit
                    is LoginStart -> {
                        LoginScreen(
                            authUrl = viewState.authUrl,
                            redirectUrl = viewState.redirectUrl,
                            onPageStart = { url -> viewModel.onPageStart(url) },
                            onAbort = { findNavController().popBackStack() }
                        )
                    }
                    is LoginComplete -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    @Composable
    private fun LoginScreen(
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
}