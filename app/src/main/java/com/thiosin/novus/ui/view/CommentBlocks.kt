package com.thiosin.novus.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.AuthorType
import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.ui.theme.*
import com.thiosin.novus.ui.utils.Border
import com.thiosin.novus.ui.utils.border

@Composable
fun CommentItem(comment: Comment, collapse: Boolean = false) {
    var isCollapsed by remember { mutableStateOf(collapse) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
            .clickable(
                onLongClick = { isCollapsed = isCollapsed.not() },
                onClick = { isCollapsed = false }
            ))
        {
            Spacer(modifier = Modifier.width((comment.depth * 8).dp))
            CommentContent(comment, isCollapsed)
        }
        if (isCollapsed.not()) {
            comment.replies.forEach { child ->
                CommentItem(comment = child, collapse = collapse)
            }
        }
    }
}

@Composable
private fun CommentContent(
    comment: Comment,
    collapse: Boolean,
//    onVoteClick: (String, Boolean?) -> Unit,
) {
    val border = getMarkerBorder(comment.depth)
//    var showControls by remember { mutableStateOf(false) }
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
//            if (showControls) {
//                Row {
//                    VoteButtons(comment.fullname)
//                }
//            }
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
                text = getVotesFormat(comment.votes),
                style = MaterialTheme.typography.caption,
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
    val color = getCommentDepthColor(depth)
    return color?.let {
        Border(strokeWidth = 2.dp, color = color)
    }
}

private fun getCommentDepthColor(depth: Int): Color? {
    return when (depth) {
        0 -> null
        1 -> deepPurple500
        2 -> blue500
        3 -> cyan500
        4 -> green500
        5 -> lime500
        6 -> yellow500
        7 -> lightGreen500
        8 -> teal500
        9 -> lightBlue500
        else -> indigo500
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommentContentPreview() {
    val comment = Comment(
        body = "This is the comment body text.",
        author = "author",
        isOP = true,
        votes = 1342,
        depth = 1,
        relativeTime = "1h ago",
        authorType = AuthorType.MODERATOR,
        replies = emptyList()
    )

    CommentContent(comment = comment, collapse = false)
}