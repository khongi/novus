package com.thiosin.novus.screens.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.view.WebContentScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : RainbowCakeFragment<WebViewState, WebViewModel> {

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @Deprecated(message = "Use newInstance instead",
        replaceWith = ReplaceWith("WebFragment.newInstance()"))
    constructor()

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
                    WebContentScreen(
                        sourceUrl = WebFragmentArgs.fromBundle(requireArguments()).url,
                        onClose = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }
}