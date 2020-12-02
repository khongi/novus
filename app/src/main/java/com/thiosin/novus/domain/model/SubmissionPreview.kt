package com.thiosin.novus.domain.model

import com.kirkbushman.araw.models.Submission

data class SubmissionPreview(
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String
)

fun Submission.toSubmissionPreview(): SubmissionPreview {
    return SubmissionPreview(
        title = title,
        subreddit = subreddit,
        author = author,
        relativeTime = "6h" // TODO calculate relative time
    )
}
