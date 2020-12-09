package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import com.thiosin.novus.domain.model.SubmissionPreview

@Composable
fun SubmissionList(
    submissions: List<SubmissionPreview>,
    listState: LazyListState,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (String) -> Unit,
    onListEnd: () -> Unit,
) {
    if (listState.firstVisibleItemIndex >= submissions.size - 10) {
        onListEnd()
    }
    val displayMetrics = AmbientContext.current.resources.displayMetrics
    val displayWidth = remember { displayMetrics.widthPixels / displayMetrics.density }

    LazyColumnForIndexed(state = listState, items = submissions) { index, submission ->
        if (index == submissions.size - 1) {
            Column {
                SubmissionPreviewItem(
                    submission = submission,
                    displayWidth = displayWidth,
                    onLinkClick = onLinkClick,
                    onDetailsClick = onDetailsClick
                )
                LoadingItem()
            }
        } else {
            SubmissionPreviewItem(
                submission = submission,
                displayWidth = displayWidth,
                onLinkClick = onLinkClick,
                onDetailsClick = onDetailsClick
            )
        }
    }
}

