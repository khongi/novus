package com.thiosin.novus.domain.model

import android.text.format.DateUtils
import com.kirkbushman.araw.models.Submission

data class SubmissionPreview(
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String
)

const val SECONDS_TO_MILLISECONDS = 1000

fun Submission.toSubmissionPreview(): SubmissionPreview {
    return SubmissionPreview(
        title = title,
        subreddit = subreddit,
        author = author,
        relativeTime = DateUtils.getRelativeTimeSpanString(
            System.currentTimeMillis(),
            created * SECONDS_TO_MILLISECONDS,
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    )
}
