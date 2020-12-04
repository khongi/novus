package com.thiosin.novus.domain.model

import android.text.format.DateUtils
import com.kirkbushman.araw.models.Submission
import timber.log.Timber

data class SubmissionPreview(
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String,
    val imageUrl: String? = null,
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
        ).toString(),
        imageUrl = getImageUrl(this)
    )
}

private fun getImageUrl(submission: Submission): String? {
    val url = when {
        submission.isVideo -> null
        submission.media?.oEmbed != null -> null
        submission.selfText.isNullOrBlank().not() -> null
        submission.thumbnailUrl.isNullOrBlank() -> null
        else -> submission.url
    }
    Timber.d("${submission.subreddit} ${submission.author}: $url")
    return url
}