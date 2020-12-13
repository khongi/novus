package com.thiosin.novus.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.ui.view.*

@Composable
fun HomeScreen(
    viewState: HomeViewState,
    onNextPage: () -> Unit,
    onSwitchSubreddit: (Subreddit) -> Unit,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (Submission) -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()
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
                onLogin = { scaffoldState.drawerState.close(onLoginClick) },
                onLogout = { scaffoldState.drawerState.close(onLogoutClick) }
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
                onVote = onVoteClick,
            )
        }
    )
}


@Composable
fun HomeContent(
    viewState: HomeViewState,
    lazyListState: LazyListState,
    onNextPage: () -> Unit,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (Submission) -> Unit,
    onVote: (String, Boolean?) -> Unit,
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
                    canVote = viewState.canVote(),
                    onLinkClick = onLinkClick,
                    onDetailsClick = onDetailsClick,
                    onListEnd = onNextPage,
                    onVote = onVote,
                )
            }
            else -> {
                LoadingScreen()
            }
        }
    }
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

private fun HomeViewState?.canVote(): Boolean {
    return when (this) {
        is HomeReady -> user != null
        else -> false
    }
}