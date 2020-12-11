package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            SubmissionContent(submission, displayWidthDp, onLinkClick, onVoteClick)
        }

        items(comments) {
            CommentItem(comment = it)
        }
    }
}

@Composable
private fun SubmissionContent(
    submission: Submission,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
    onVoteClick: (Submission) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(top = 4.dp)) {
            InfoRow(submission)
            TitleRow(submission)
            if (submission.selfText.isBlank().not()) {
                SelfText(submission.selfText)
            }
            submission.media?.let {
                MediaRow(it, displayWidthDp.dp)
            }
            Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                val liked = remember { mutableStateOf(submission.likes) }
                Votes(submission.votes)
                UpVoteButton(submission = submission, liked = liked, onClick = onVoteClick)
                DownVoteButton(submission = submission, liked = liked, onClick = onVoteClick)
                LinkButton(submission.link, onLinkClick)
            }
        }
    }
}

@Composable
fun SelfText(text: String) {
    Text(text = text,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
}
