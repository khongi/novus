package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

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
        viewModel.load("all")
    }

    override fun render(viewState: HomeViewState) {
        (view as ComposeView).setContent {
            NovusTheme {
                when (viewState) {
                    is HomeInitial -> Unit
                    is HomeReady -> HomeContent(
                        showLoading = viewState.showLoading,
                        listState = viewState.listState,
                        subreddit = viewState.subreddit
                    )
                }
            }
        }
    }
}