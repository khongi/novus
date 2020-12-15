package com.thiosin.novus.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionDetails(
    submission: Submission,
    comments: List<Comment>,
    canVote: Boolean,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
    onCommentVoteClick: (String, Boolean?) -> Unit,
) {
    LazyColumn {
        item {
            SubmissionPreview(
                submission = submission,
                showSelfText = true,
                canVote = canVote,
                displayWidthDp = displayWidthDp,
                onLinkClick = onLinkClick,
                onVoteClick = onVoteClick
            )
        }

        items(comments) {
            CommentItem(
                comment = it,
                canVote = canVote,
                onVoteClick = onCommentVoteClick
            )
        }
    }
}
