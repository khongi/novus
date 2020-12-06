package com.thiosin.novus.ui.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import com.thiosin.novus.domain.model.SubmissionPreview

@Composable
fun SubmissionList(
    submissions: List<SubmissionPreview>,
    listState: LazyListState,
    onLinkClick: (String) -> Unit,
    onListEnd: () -> Unit,
) {
    if (listState.firstVisibleItemIndex >= submissions.size - 10) {
        onListEnd()
    }
    val displayMetrics = ContextAmbient.current.resources.displayMetrics
    val displayWidth = remember { displayMetrics.widthPixels / displayMetrics.density }
    LazyColumn() {
        items(submissions) { submission ->
            SubmissionPreviewItem(submission = submission, displayWidth, onLinkClick = onLinkClick)
        }
    }
}

