package com.thiosin.novus.screens.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.navigation.navigator
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.screens.home.HomeFragment
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
                        LoginScreen(authUrl = viewState.authUrl) { url ->
                            viewModel.onAuthPageStart(url)
                        }
                    }
                    is LoginComplete -> {
                        navigator?.replace(HomeFragment())
                    }
                }
            }
        }
    }

    @Composable
    private fun LoginScreen(authUrl: String, onPageStart: (url: String) -> Unit) {
        val browser = remember {
            WebView(requireContext()).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                        onPageStart(url)
                    }
                }
            }
        }

        browser.loadUrl(authUrl)

        AndroidView({ browser })
    }
}