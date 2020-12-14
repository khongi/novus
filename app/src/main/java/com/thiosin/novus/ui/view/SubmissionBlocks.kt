package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.ui.utils.shortenToThousands

@Composable
fun SubmissionInfoRow(submission: Submission) {
    Row(modifier = Modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = submission.subreddit,
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.subtitle2,
        )
        Text(
            text = submission.author,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        Text(
            text = submission.relativeTime,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
    }
}

@Composable
fun SubmissionStatRow(submission: Submission) {
    Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = getVotesFormat(submission.votes),
            style = MaterialTheme.typography.subtitle1,
        )
        Text(
            text = "${shortenToThousands(submission.comments)} comments",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun SubmissionTitleRow(submission: Submission) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (submission.media == null && submission.thumbnail != null) {
            Thumbnail(submission.thumbnail)
        }
        SubmissionTitle(submission.title)
    }
}

@Composable
fun SubmissionSelfText(text: String) {
    Text(text = text,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
}

@Composable
fun SubmissionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@Composable
fun SubmissionMediaRow(media: SubmissionMedia, availableWidth: Dp) {
    val ratio = availableWidth.div(media.width)
    val height = ratio.times(media.height)
    Box(modifier = Modifier.width(availableWidth).height(height)) {
        Media(media = media)
    }
}

@Composable
fun SubmissionButtonRow(
    submission: Submission,
    canVote: Boolean,
    onLinkClick: (String) -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
    onCommentsClick: ((Submission) -> Unit)? = null,
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (canVote) {
            VoteButtons(submission.fullname, submission.liked, onVoteClick)
        }
        if (onCommentsClick != null) {
            CommentsButton(submission = submission, onClick = onCommentsClick)
        }
        LinkButton(url = submission.link, onClick = onLinkClick)
    }
}
