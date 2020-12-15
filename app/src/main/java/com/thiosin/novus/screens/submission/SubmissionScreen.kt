package com.thiosin.novus.screens.submission

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar
import com.thiosin.novus.ui.view.SubmissionDetails

@Composable
fun SubmissionScreen(
    viewState: SubmissionReadyState,
    onNavigationClick: () -> Unit,
    onLinkClick: (String) -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
    onCommentVoteClick: (String, Boolean?) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NovusTopAppBar(
                title = "Comments",
                navIcon = NavigationIcon.Back,
                onNavigationIconClick = onNavigationClick
            )
        },
        bodyContent = {
            if (viewState.loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            SubmissionDetails(
                submission = viewState.submission,
                comments = viewState.comments,
                canVote = viewState.canVote,
                displayWidthDp = viewState.displayWidthDp,
                onLinkClick = onLinkClick,
                onVoteClick = onVoteClick,
                onCommentVoteClick = onCommentVoteClick
            )
        }
    )
}
