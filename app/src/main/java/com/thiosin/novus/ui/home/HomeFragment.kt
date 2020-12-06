package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.navigation.navigator
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.ui.common.NavigationIcon
import com.thiosin.novus.ui.common.NovusDrawer
import com.thiosin.novus.ui.common.NovusTopAppBar
import com.thiosin.novus.ui.common.SubmissionList
import com.thiosin.novus.ui.home.HomeViewModel.ShowLinkEvent
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.web.WebFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

    override fun provideViewModel() = getViewModel()
    override fun render(viewState: HomeViewState) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NovusTheme {
                    val state = viewModel.state.observeAsState()
                    val currentValue = state.value
                    if (currentValue != null) {
                        HomeScreen(currentValue)
                    }
                }
            }
        }
    }

    @Composable
    private fun HomeScreen(viewState: HomeViewState) {
        val scaffoldState = rememberScaffoldState()
        val listState = rememberLazyListState()

        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            drawerContent = {
                NovusDrawer(
                    drawerItems = viewState.getSubreddits(),
                    onClick = { subreddit ->
                        viewModel.load(subreddit)
                        scaffoldState.drawerState.close()
                    }
                )
            },
            drawerBackgroundColor = MaterialTheme.colors.background,
            drawerContentColor = MaterialTheme.colors.onBackground,
            scaffoldState = scaffoldState,
            topBar = {
                NovusTopAppBar(
                    title = viewState.getTitle(),
                    navIcon = NavigationIcon.Menu,
                    onNavigationIconClick = {
                        scaffoldState.drawerState.open()
                    }
                )
            },
            bodyContent = {
                Column(modifier = Modifier.fillMaxSize()) {
                    when (viewState) {
                        is HomeReady -> {
                            if (viewState.loading) {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                            SubmissionList(
                                submissions = viewState.submissions,
                                listState = listState,
                                onLinkClick = { viewModel.showLink(it) },
                                onListEnd = { viewModel.loadNextPage() }
                            )
                        }
                        else -> {
                            LoadingScreen()
                        }
                    }
                }
            }
        )
    }

    @Composable
    private fun LoadingScreen() {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load("all")
    }

    private fun HomeViewState?.getTitle(): String {
        return when (this) {
            is HomeReady -> "/r/${subreddit}"
            else -> "Loading"
        }
    }

    private fun HomeViewState?.getSubreddits(): List<Subreddit> {
        return when (this) {
            is HomeReady -> listOf(Subreddit("all"), Subreddit("funny"))
            else -> listOf()
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