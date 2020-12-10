package com.thiosin.novus.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thiosin.novus.domain.model.AuthorType
import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.ui.modifiers.Border
import com.thiosin.novus.ui.modifiers.border
import com.thiosin.novus.ui.theme.*

@Composable
fun SubmissionDetails(
    submission: Submission,
    comments: List<Comment>,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
) {
    LazyColumn {
        item {
            SubmissionContent(submission, displayWidthDp, onLinkClick)
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
) {
    Column(modifier = Modifier.padding(top = 4.dp)) {
        InfoRow(submission)
        TitleRow(submission)
        submission.media?.let {
            MediaRow(it, displayWidthDp.dp)
        }
        Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Votes(submission.votes)

            LinkButton(submission.link, onLinkClick)
        }
    }
}

@Composable
fun CommentItem(comment: Comment, collapse: Boolean = false) {
    val isCollapsed = remember { mutableStateOf(collapse) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
            .clickable(
                onLongClick = { isCollapsed.value = isCollapsed.value.not() },
                onClick = { isCollapsed.value = false }
            ))
        {
            Spacer(modifier = Modifier.width((comment.depth * 8).dp))
            CommentContent(comment, isCollapsed.value)
        }
        if (isCollapsed.value.not()) {
            comment.replies.forEach { child ->
                CommentItem(comment = child, collapse = collapse)
            }
        }
    }
}

@Composable
private fun CommentContent(comment: Comment, collapse: Boolean) {
    val border = getMarkerBorder(comment.depth)
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().border(start = border).padding(start = 8.dp)) {
            CommentHeader(comment, collapse)
            if (collapse.not()) {
                Text(
                    text = comment.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        Divider(thickness = 0.25.dp)
    }
}

@Composable
private fun CommentHeader(
    comment: Comment,
    collapse: Boolean,
) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val authorColor = getAuthorColor(comment)
            Text(
                text = comment.author,
                style = MaterialTheme.typography.subtitle1,
                color = authorColor
            )
            Text(
                text = "↑ ${comment.votes}",
                style = MaterialTheme.typography.subtitle1.plus(
                    TextStyle(fontSize = 12.sp)
                ),
                modifier = Modifier.padding(start = 12.dp),
            )
            if (collapse) {
                Text(
                    text = "+[${comment.replies.size}]",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.caption
                )
            }
        }
        Text(
            text = comment.relativeTime,
            style = MaterialTheme.typography.overline,
        )
    }
}

@Composable
fun Marker() {
    Column(modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier.preferredWidth(4.dp).fillMaxHeight().background(Color.Red)
        )
    }
}

private fun getAuthorColor(comment: Comment): Color {
    return when (comment.authorType) {
        AuthorType.NORMAL -> {
            if (comment.isOP) {
                purple700
            } else {
                Color.Unspecified
            }
        }
        AuthorType.MODERATOR -> green500
        AuthorType.ADMIN -> redditOrange
    }
}

private fun getMarkerBorder(depth: Int): Border? {
    val color = when (depth) {
        0 -> return null
        1 -> deepPurple500
        2 -> blue500
        3 -> cyan500
        4 -> green500
        else -> lime500
    }
    return Border(strokeWidth = 2.dp, color = color)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommentContentPreview() {
    val comment = Comment(
        body = "This is the comment body text.",
        author = "author",
        isOP = true,
        votes = 123,
        depth = 1,
        relativeTime = "1h ago",
        authorType = AuthorType.MODERATOR,
        replies = emptyList()
    )

    CommentContent(comment = comment, collapse = false)
}