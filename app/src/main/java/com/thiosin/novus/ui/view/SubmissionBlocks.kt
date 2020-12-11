package com.thiosin.novus.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia


@Composable
fun InfoRow(submission: Submission) {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
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
fun TitleRow(submission: Submission) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (submission.media == null && submission.thumbnail != null) {
            Thumbnail(submission.thumbnail)
        }
        SubmissionTitle(submission.title)
    }
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
fun MediaRow(media: SubmissionMedia, availableWidth: Dp) {
    val ratio = availableWidth.div(media.width)
    val height = ratio.times(media.height)
    Box(modifier = Modifier.width(availableWidth).height(height)) {
        Media(media = media)
    }
}

@Composable
fun SubmissionButtonRow(
    submission: Submission,
    onLinkClick: (String) -> Unit,
    onCommentsClick: (Submission) -> Unit,
    onVoteClick: (Submission) -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val liked = remember { mutableStateOf(submission.likes) }
        Votes(votes = submission.votes)
        UpVoteButton(submission = submission, liked = liked, onClick = onVoteClick)
        DownVoteButton(submission = submission, liked = liked, onClick = onVoteClick)
        CommentsButton(submission = submission, onClick = onCommentsClick)
        LinkButton(url = submission.link, onClick = onLinkClick)
    }
}

@Composable
fun SubmissionIconButton(
    submission: Submission,
    iconId: Int,
    onClick: (Submission) -> Unit,
    color: Color = Color.Unspecified
) {
    IconButton(onClick = { onClick(submission) }) {
        Icon(imageVector = vectorResource(id = iconId), tint = color)
    }
}

@Composable
fun SubmissionIconButtonWithText(
    submission: Submission,
    text: AnnotatedString,
    iconId: Int,
    onClick: (Submission) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onClick(submission) })
    ) {
        Spacer(Modifier.size(8.dp))
        Icon(imageVector = vectorResource(id = iconId))
        Text(
            text = text,
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.caption
        )
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
fun UpVoteButton(
    submission: Submission,
    liked: MutableState<Boolean?>,
    onClick: (Submission) -> Unit
) {
    val upVoted = liked.value == true
    val icon = if (upVoted) {
        R.drawable.ic_baseline_thumb_up_24
    } else {
        R.drawable.ic_outline_thumb_up_24
    }
    SubmissionIconButton(
        submission = submission,
        iconId = icon,
        onClick = {
            liked.value = if (liked.value != true) {
                true
            } else {
                null
            }
            onClick(submission.copy(likes = liked.value))
        },
        color = if (upVoted) getVotesColor(true) else MaterialTheme.colors.onSurface
    )
}

@Composable
fun DownVoteButton(
    submission: Submission,
    liked: MutableState<Boolean?>,
    onClick: (Submission) -> Unit
) {
    val downVoted = liked.value == false
    val icon = if (downVoted) {
        R.drawable.ic_baseline_thumb_down_24
    } else {
        R.drawable.ic_outline_thumb_down_24
    }
    SubmissionIconButton(
        submission = submission,
        iconId = icon,
        onClick = {
            liked.value = if (liked.value != false) {
                false
            } else {
                null
            }
            onClick(submission.copy(likes = liked.value))
        },
        color = if (downVoted) getVotesColor(false) else MaterialTheme.colors.onSurface
    )
}

@Composable
fun CommentsButton(
    submission: Submission,
    onClick: (Submission) -> Unit,
) {
    SubmissionIconButtonWithText(
        submission = submission,
        text = annotatedString { append(submission.comments.toString()) },
        iconId = R.drawable.ic_outline_mode_comment_24,
        onClick = onClick
    )
}

@Composable
fun LinkButton(
    url: String,
    onClick: (String) -> Unit,
) {
    IconButton(onClick = { onClick(url) }) {
        Icon(imageVector = vectorResource(id = R.drawable.ic_outline_link_24))
    }
}
