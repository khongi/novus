package com.thiosin.novus.ui.view

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    ScrollableColumn {
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

        Spacer(modifier = Modifier.height(16.dp))

        comments.forEach {
            CommentItem(comment = it)
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommentContent(comment)
        Spacer(modifier = Modifier.height(8.dp))
        comment.replies.forEach { child ->
            CommentItem(comment = child)
        }
    }
}

@Composable
private fun CommentContent(comment: Comment) {
    Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp)) {
        Spacer(modifier = Modifier.width((comment.depth * 8).dp))
        val border = getMarkerBorder(comment.depth)
        Column(modifier = Modifier
            .fillMaxWidth()
            .border(start = border)
            .padding(start = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = comment.author,
                        style = MaterialTheme.typography.subtitle1,
                        color = if (comment.isOP) {
                            MaterialTheme.colors.primary
                        } else {
                            Color.Unspecified
                        }
                    )
                    Text(
                        text = "↑ ${comment.votes}",
                        style = MaterialTheme.typography.subtitle1.plus(
                            TextStyle(fontSize = 12.sp)
                        ),
                        modifier = Modifier.padding(start = 12.dp),
                    )
                }
                Text(
                    text = comment.relativeTime,
                    style = MaterialTheme.typography.overline,
                )
            }
            Text(
                text = comment.body,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

fun getMarkerBorder(depth: Int): Border? {
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

@Composable
fun Marker() {
    Column(modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier.preferredWidth(4.dp).fillMaxHeight().background(Color.Red)
        )
    }
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
        isCollapsed = false,
        relativeTime = "1h ago",
        replies = emptyList()
    )

    CommentContent(comment = comment)
}