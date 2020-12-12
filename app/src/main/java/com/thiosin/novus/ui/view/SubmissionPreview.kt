package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionPreview(
    submission: Submission,
    showSelfText: Boolean,
    canVote: Boolean,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
    onCommentsClick: ((Submission) -> Unit)? = null,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(top = 4.dp)) {
            SubmissionInfoRow(submission)
            SubmissionTitleRow(submission)
            if (showSelfText && submission.selfText.isNotBlank()) {
                SubmissionSelfText(submission.selfText)
            }
            submission.media?.let {
                // Screen width - Horizontal padding
                SubmissionMediaRow(it, displayWidthDp.dp - 16.dp)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                SubmissionStatRow(submission)
                SubmissionButtonRow(
                    submission = submission,
                    canVote = canVote,
                    onLinkClick = onLinkClick,
                    onVoteClick = onVoteClick,
                    onCommentsClick = onCommentsClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val submission = Submission(
        id = "jqnn6m",
        fullname = "t3_jqnn6m",
        title = "Kotlin plugin updated to add data class, sealed class, annotations quicker",
        author = "VincentJoshuaET",
        subreddit = "androiddev",
        relativeTime = "1 month ago",
        votes = 1234,
        comments = 9,
        link = "https://www.reddit.com/r/androiddev/comments/jqnn6m/kotlin_plugin_updated_to_add_data_class_sealed/",
        thumbnail = "",
        selfText = "This is the self text"
    )
    SubmissionPreview(
        submission = submission,
        showSelfText = true,
        canVote = true,
        displayWidthDp = 300F,
        onLinkClick = {},
        onVoteClick = { _: String, _: Boolean? -> },
    )
}

