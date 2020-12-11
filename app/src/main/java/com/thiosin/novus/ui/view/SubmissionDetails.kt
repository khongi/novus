package com.thiosin.novus.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionDetails(
    submission: Submission,
    comments: List<Comment>,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
    onVoteClick: (Submission) -> Unit,
) {
    LazyColumn {
        item {
            SubmissionPreview(
                submission = submission,
                showSelfText = true,
                displayWidthDp = displayWidthDp,
                onLinkClick = onLinkClick,
                onVoteClick = onVoteClick
            )
        }

        items(comments) {
            CommentItem(comment = it)
        }
    }
}

