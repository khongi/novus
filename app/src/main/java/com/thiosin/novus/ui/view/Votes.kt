package com.thiosin.novus.ui.view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import com.thiosin.novus.ui.theme.redditDownVote
import com.thiosin.novus.ui.theme.redditUpvote
import com.thiosin.novus.ui.utils.shortenToThousands

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
