package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.ui.theme.redditDownVote
import com.thiosin.novus.ui.theme.redditUpvote
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

fun getVotesFormat(votes: Int): AnnotatedString {
    val formattedValue = shortenToThousands(votes)

    return annotatedString {
        withStyle(SpanStyle(color = getVotesColor(votes > 0))) {
            append(formattedValue)
        }
    }
}

fun getVotesColor(liked: Boolean): Color {
    return when (liked) {
        true -> {
            redditUpvote
        }
        false -> {
            redditDownVote
        }
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
    onLinkClick: (String) -> Unit,
    onVoteClick: (String, Boolean?) -> Unit,
    onCommentsClick: ((Submission) -> Unit)? = null,
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VoteButtons(submission.fullname, submission.likes, onVoteClick)
        if (onCommentsClick != null) {
            CommentsButton(submission = submission, onClick = onCommentsClick)
        }
        LinkButton(url = submission.link, onClick = onLinkClick)
    }
}

@Composable
fun VoteButtons(
    fullname: String,
    liked: Boolean?,
    onVoteClick: (String, Boolean?) -> Unit,
) {
    val mutableLiked = remember { mutableStateOf(liked) }
    UpVoteButton(fullname = fullname, liked = mutableLiked, onClick = onVoteClick)
    DownVoteButton(fullname = fullname, liked = mutableLiked, onClick = onVoteClick)
}

@Composable
fun UpVoteButton(
    fullname: String,
    liked: MutableState<Boolean?>,
    onClick: (String, Boolean?) -> Unit,
) {
    val upVoted = liked.value == true
    val icon = if (upVoted) {
        R.drawable.ic_baseline_thumb_up_24
    } else {
        R.drawable.ic_outline_thumb_up_24
    }
    val tint = if (upVoted) getVotesColor(true) else MaterialTheme.colors.onSurface
    IconButton(onClick = {
        liked.value = if (liked.value != true) {
            true
        } else {
            null
        }
        onClick(fullname, liked.value)
    }) {
        Icon(imageVector = vectorResource(id = icon), tint = tint)
    }
}

@Composable
fun DownVoteButton(
    fullname: String,
    liked: MutableState<Boolean?>,
    onClick: (String, Boolean?) -> Unit,
) {
    val downVoted = liked.value == false
    val icon = if (downVoted) {
        R.drawable.ic_baseline_thumb_down_24
    } else {
        R.drawable.ic_outline_thumb_down_24
    }
    val tint = if (downVoted) getVotesColor(false) else MaterialTheme.colors.onSurface
    IconButton(onClick = {
        liked.value = if (liked.value != false) {
            false
        } else {
            null
        }
        onClick(fullname, liked.value)
    }) {
        Icon(imageVector = vectorResource(id = icon), tint = tint)
    }
}

@Composable
fun CommentsButton(
    submission: Submission,
    onClick: (Submission) -> Unit,
) {
    IconButton(onClick = { onClick(submission) }) {
        Icon(imageVector = vectorResource(id = R.drawable.ic_outline_mode_comment_24))
    }
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
