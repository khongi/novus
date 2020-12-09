package com.thiosin.novus.domain.model

import android.text.format.DateUtils
import com.thiosin.novus.data.network.model.submission.PostHint
import com.thiosin.novus.data.network.model.submission.SubmissionListingChildData
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse

data class SubmissionPreview(
    val fullname: String,
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String,
    val link: String,
    val votes: String,
    val comments: Int,
    val media: SubmissionMedia? = null,
)

data class SubmissionMedia(
    val url: String,
    val type: SubmissionMediaType,
    val width: Int,
    val height: Int,
)

enum class SubmissionMediaType {
    Image,
    Video,
    Thumbnail
}

fun SubmissionListingResponse.toLoadResultData(): List<SubmissionPreview> {
    val children = this.data.children
    return children.map { child ->
        child.data.let {
            SubmissionPreview(
                fullname = child.data.name,
                title = it.title,
                subreddit = it.subreddit,
                author = it.author,
                link = it.url,
                comments = it.numComments.toInt(),
                votes = it.ups.toVoteFormat(),
                relativeTime = getRelativeTime(it),
                media = getSubmissionMedia(it),
            )
        }
    }
}

private fun Long.toVoteFormat(): String {
    if (this < 1000) {
        return this.toString()
    }
    val k = this / 1000.0
    return "%.${1}fk".format(k)
}

private fun getRelativeTime(it: SubmissionListingChildData): String {
    return DateUtils.getRelativeTimeSpanString(
        System.currentTimeMillis(),
        it.created * 1_000L,
        DateUtils.SECOND_IN_MILLIS
    ).toString()
}

private fun getSubmissionMedia(submission: SubmissionListingChildData): SubmissionMedia? {
    var url: String? = null
    var type = SubmissionMediaType.Thumbnail
    when (submission.postHint) {
        PostHint.Link -> {
            when {
                submission.domain == "i.imgur.com" && submission.url.contains(".gifv") -> {
                    url = submission.url.replace(".gifv", ".mp4")
                    type = SubmissionMediaType.Video
                }
                submission.thumbnail != null && submission.thumbnail.startsWith("http") -> {
                    url = submission.thumbnail
                    type = SubmissionMediaType.Thumbnail
                }
            }
        }
        PostHint.Image -> {
            url = submission.url
            type = SubmissionMediaType.Image
        }
        PostHint.HostedVideo -> {
            // Remove end of URL until .mp4
            url = submission.media?.redditVideo?.fallbackURL?.dropLastWhile { !it.isDigit() }
            type = SubmissionMediaType.Video
        }
        else -> Unit
    }

    val sourcePreview = submission.preview?.images?.get(0)?.source
    val width = sourcePreview?.width?.toInt() ?: submission.thumbnailWidth ?: 200
    val height = sourcePreview?.height?.toInt() ?: submission.thumbnailHeight ?: 200

    return url?.let { SubmissionMedia(url = it, type = type, width = width, height = height) }
}