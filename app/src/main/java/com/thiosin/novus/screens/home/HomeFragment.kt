package com.thiosin.novus.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
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
                            onLogin = {
                                viewModel.startLoading()
                                scaffoldState.drawerState.close()
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                                )
                            },
                            onLogout = {
                                scaffoldState.drawerState.close()
                                viewModel.switchToUserlessMode()
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun HomeScreen(
        scaffoldState: ScaffoldState,
        viewState: HomeViewState,
        lazyListState: LazyListState,
        onNextPage: () -> Unit,
        onSwitchSubreddit: (Subreddit) -> Unit,
        onLinkClick: (String) -> Unit,
        onDetailsClick: (Submission) -> Unit,
        onLogin: () -> Unit,
        onLogout: () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            scaffoldState = scaffoldState,
            topBar = {
                NovusTopAppBar(
                    title = viewState.getTitle(),
                    navIcon = NavigationIcon.Menu,
                    onNavigationIconClick = { scaffoldState.drawerState.open() }
                )
            },
            drawerContent = {
                NovusDrawer(
                    subreddits = viewState.getSubreddits(),
                    onSubredditSelect = { subreddit ->
                        onSwitchSubreddit(subreddit)
                        scaffoldState.drawerState.close()
                    },
                    selected = viewState.getCurrentSubreddit(),
                    user = viewState.getUser(),
                    onLogin = onLogin,
                    onLogout = onLogout
                )
            },
            drawerBackgroundColor = MaterialTheme.colors.background,
            drawerContentColor = MaterialTheme.colors.onBackground,
            bodyContent = {
                HomeContent(
                    viewState = viewState,
                    lazyListState = lazyListState,
                    onNextPage = onNextPage,
                    onLinkClick = onLinkClick,
                    onDetailsClick = onDetailsClick,
                )
            }
        )
    }

    @Composable
    private fun HomeContent(
        viewState: HomeViewState,
        lazyListState: LazyListState,
        onNextPage: () -> Unit,
        onLinkClick: (String) -> Unit,
        onDetailsClick: (Submission) -> Unit,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (viewState) {
                is HomeReady -> {
                    if (viewState.loading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                    SubmissionList(
                        submissions = viewState.submissions,
                        lazyListState = lazyListState,
                        onLinkClick = onLinkClick,
                        onDetailsClick = onDetailsClick,
                        onListEnd = onNextPage
                    )
                }
                else -> {
                    LoadingScreen()
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

    private fun HomeViewState?.getTitle(): String {
        return when (this) {
            is HomeReady -> selectedSubreddit.displayName
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
            is HomeReady -> selectedSubreddit
            else -> null
        }
    }

    private fun HomeViewState?.getUser(): User? {
        return when (this) {
            is HomeReady -> user
            else -> null
        }
    }
}