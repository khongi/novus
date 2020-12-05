package com.thiosin.novus.ui.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.common.WebContent
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : RainbowCakeFragment<WebViewState, WebViewModel> {

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @Deprecated(message = "Use newInstance instead",
        replaceWith = ReplaceWith("WebFragment.newInstance()"))
    constructor()

    companion object {
        private const val URL_KEY = "URL_KEY"
        fun newInstance(url: String): WebFragment {
            @Suppress("DEPRECATION")
            return WebFragment().applyArgs {
                putString(URL_KEY, url)
            }
        }
    }

    override fun provideViewModel() = getViewModel()
    override fun render(viewState: WebViewState) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                NovusTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar() {

                            }
                        },
                        bodyContent = {
                            WebContent(url = requireArguments().getString(URL_KEY)
                                ?: throw IllegalStateException("URL is null"))
                        }
                    )
                }
            }
        }
    }
}