package com.thiosin.novus.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.view.*
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
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                NovusTheme {
                    val state = viewModel.state.observeAsState()
                    state.value?.let {
                        HomeScreen(it)
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
                    subreddits = viewState.getSubreddits(),
                    onClick = { subreddit ->
                        viewModel.switchSubreddit(subreddit)
                        scaffoldState.drawerState.close()
                    },
                    selected = viewState.getCurrentSubreddit()
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
                                onLinkClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToWebFragment(it)
                                    )
                                },
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

    private fun HomeViewState?.getTitle(): String {
        return when (this) {
            is HomeReady -> currentSubreddit.displayName
            else -> "Loading..."
        }
    }

    private fun HomeViewState?.getSubreddits(): List<Subreddit> {
        return when (this) {
            is HomeReady -> subreddits
            else -> listOf()
        }
    }

    private fun HomeViewState?.getCurrentSubreddit(): Subreddit? {
        return when (this) {
            is HomeReady -> currentSubreddit
            else -> null
        }
    }
}