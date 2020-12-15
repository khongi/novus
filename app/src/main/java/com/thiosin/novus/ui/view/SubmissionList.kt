package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.ui.utils.getDisplayWidth

@Composable
fun SubmissionList(
    submissions: List<Submission>,
    lazyListState: LazyListState,
    canVote: Boolean,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (Submission) -> Unit,
    onListEnd: () -> Unit,
    onVote: (String, Boolean?) -> Unit,
) {
    if (lazyListState.firstVisibleItemIndex >= submissions.size - 10) {
        onListEnd()
    }
    val displayWidthDp = getDisplayWidth(AmbientContext.current)
    val displayWidth = remember { displayWidthDp }

    LazyColumnForIndexed(state = lazyListState, items = submissions) { index, submission ->
        if (index == submissions.size - 1) {
            Column {
                SubmissionPreview(
                    submission = submission,
                    showSelfText = false,
                    canVote = canVote,
                    displayWidthDp = displayWidth,
                    onLinkClick = onLinkClick,
                    onCommentsClick = onDetailsClick,
                    onVoteClick = onVote,
                )
                LoadingItem()
            }
        } else {
            SubmissionPreview(
                submission = submission,
                showSelfText = false,
                canVote = canVote,
                displayWidthDp = displayWidth,
                onLinkClick = onLinkClick,
                onVoteClick = onVote,
                onCommentsClick = onDetailsClick,
            )
        }
    }
}
