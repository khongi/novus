package com.thiosin.novus.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

    override fun provideViewModel() = getViewModel()
    override fun render(viewState: HomeViewState) = Unit

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(inflater.context).apply {
            setContent {
                NovusTheme {
                    val state = viewModel.state.observeAsState()
                    val scaffoldState = rememberScaffoldState()
                    val lazyListState = rememberLazyListState()

                    state.value?.let { viewState ->
                        HomeScreen(
                            scaffoldState = scaffoldState,
                            viewState = viewState,
                            lazyListState = lazyListState,
                            onNextPage = { viewModel.loadNextPage() },
                            onSwitchSubreddit = { subreddit -> viewModel.switchSubreddit(subreddit) },
                            onLinkClick = { url -> navigateToWebFragment(url) },
                            onDetailsClick = { submission -> navigateToDetails(submission) },
                            onLoginClick = {
                                viewModel.startLoading()
                                scaffoldState.drawerState.close()
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                                )
                            },
                            onLogoutClick = {
                                scaffoldState.drawerState.close()
                                viewModel.switchToUserlessMode()
                            },
                            onVoteClick = { fullname, liked ->
                                viewModel.vote(fullname, liked)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun navigateToDetails(submission: Submission) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToSubmissionFragment(submission)
        )
    }

    private fun navigateToWebFragment(url: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToWebFragment(url)
        )
    }
}