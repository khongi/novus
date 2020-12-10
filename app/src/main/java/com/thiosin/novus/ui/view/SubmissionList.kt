package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionList(
    submissions: List<Submission>,
    lazyListState: LazyListState,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (Submission) -> Unit,
    onListEnd: () -> Unit,
) {
    if (lazyListState.firstVisibleItemIndex >= submissions.size - 10) {
        onListEnd()
    }
    val displayWidthDp = AmbientContext.current.getDisplayWidthDp()
    val displayWidth = remember { displayWidthDp }

    LazyColumnForIndexed(state = lazyListState, items = submissions) { index, submission ->
        if (index == submissions.size - 1) {
            Column {
                SubmissionPreview(
                    submission = submission,
                    displayWidthDp = displayWidth,
                    onLinkClick = onLinkClick,
                    onDetailsClick = onDetailsClick
                )
                LoadingItem()
            }
        } else {
            SubmissionPreview(
                submission = submission,
                displayWidthDp = displayWidth,
                onLinkClick = onLinkClick,
                onDetailsClick = onDetailsClick
            )
        }
    }
}

