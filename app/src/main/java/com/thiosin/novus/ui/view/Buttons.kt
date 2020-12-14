package com.thiosin.novus.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.Submission

@Composable
fun LinkButton(
    url: String,
    onClick: (String) -> Unit,
) {
    IconButton(onClick = { onClick(url) }) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_link),
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
fun CommentsButton(
    submission: Submission,
    onClick: (Submission) -> Unit,
) {
    IconButton(onClick = { onClick(submission) }) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_comment),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DownVoteButton(
    fullname: String,
    liked: Boolean?,
    onClick: (String, Boolean?) -> Unit,
) {
    val downVoted = liked == false
    val tint = if (downVoted) getVotesColor(false) else null
    val colorFilter = tint?.let { ColorFilter.tint(tint) }
    IconButton(onClick = {
        val newLikedValue = if (liked != false) {
            false
        } else {
            null
        }
        onClick(fullname, newLikedValue)
    }) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_dislike),
            modifier = Modifier.size(24.dp),
            colorFilter = colorFilter
        )
    }
}

@Composable
fun UpVoteButton(
    fullname: String,
    liked: Boolean?,
    onClick: (String, Boolean?) -> Unit,
) {
    val upVoted = liked == true
    val tint = if (upVoted) getVotesColor(true) else null
    val colorFilter = tint?.let { ColorFilter.tint(tint) }
    IconButton(onClick = {
        val newLikedValue = if (liked != true) {
            true
        } else {
            null
        }
        onClick(fullname, newLikedValue)
    }) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_like),
            modifier = Modifier.size(24.dp),
            colorFilter = colorFilter
        )
    }
}

@Composable
fun VoteButtons(
    fullname: String,
    liked: Boolean?,
    onVoteClick: (String, Boolean?) -> Unit,
) {
    UpVoteButton(fullname = fullname, liked = liked, onClick = onVoteClick)
    DownVoteButton(fullname = fullname, liked = liked, onClick = onVoteClick)
}