package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.navigation.navigator
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.common.NovusTopAppBar
import com.thiosin.novus.ui.common.SubmissionList
import com.thiosin.novus.ui.home.HomeViewModel.ShowLinkEvent
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.web.WebFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

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
        viewModel.load("all")
    }

    override fun render(viewState: HomeViewState) {
        (view as ComposeView).setContent {
            NovusTheme {
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    topBar = {
                        NovusTopAppBar(
                            title = viewState.getTitle()
                        )
                    },
                    bodyContent = {
                        when (viewState) {
                            is HomeInitial -> {
                                Column(modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                            is HomeReady -> {
                                SubmissionList(
                                    listFlow = viewState.listState
                                ) {
                                    viewModel.showLink(it)
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun HomeViewState.getTitle(): String {
        return when (this) {
            HomeInitial -> "Loading"
            is HomeReady -> "/r/${subreddit}"
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is ShowLinkEvent -> {
                navigator?.add(WebFragment.newInstance(event.url))
            }
        }
    }
}