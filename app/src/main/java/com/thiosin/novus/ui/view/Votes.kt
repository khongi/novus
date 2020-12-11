package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.thiosin.novus.ui.theme.redditDownVote
import com.thiosin.novus.ui.theme.redditUpvote
import kotlin.math.absoluteValue

@Composable
fun Votes(votes: Int) {
    Text(
        text = getVotesFormat(votes),
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(4.dp)
    )
}

fun getVotesFormat(votes: Int): AnnotatedString {
    val abs = votes.absoluteValue
    val formattedValue = if (abs < 1000) {
        "$abs"
    } else {
        "%.${1}fk".format(abs / 1000.0)
    }

    return annotatedString {
        withStyle(
            SpanStyle(
                color = getVotesColor(votes > 0)
            )
        ) {
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