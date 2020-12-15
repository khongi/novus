package com.thiosin.novus.domain.model

import android.os.Parcelable
import com.thiosin.novus.data.network.model.submission.PostHint
import com.thiosin.novus.data.network.model.submission.SubmissionListingChildData
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Submission(
    val id: String,
    val fullname: String,
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String,
    val link: String,
    val votes: Int,
    val comments: Int,
    val selfText: String,
    val thumbnail: String? = null,
    val media: SubmissionMedia? = null,
    var liked: Boolean? = null,
) : Parcelable

@Parcelize
data class SubmissionMedia(
    val url: String,
    val type: SubmissionMediaType,
    val width: Int,
    val height: Int,
) : Parcelable

@Parcelize
enum class SubmissionMediaType : Parcelable {
    Image,
    Video,
}

fun SubmissionListingResponse.toLoadResultData(): List<Submission> {
    val children = this.data.children
    return children.map { child ->
        child.data.let {
            Submission(
                id = it.id,
                fullname = child.data.name,
                title = it.title,
                subreddit = it.subreddit,
                author = it.author,
                link = it.url,
                comments = it.numComments.toInt(),
                selfText = it.selftext?.trim() ?: "",
                votes = it.score.toInt(),
                relativeTime = getRelativeTime(it.createdUTC.toLong()),
                thumbnail = getThumbnail(it.thumbnail),
                media = getSubmissionMedia(it),
                liked = it.likes,
            )
        }
    }
}

private fun getThumbnail(url: String?): String? {
    if (url != null && url.startsWith("http")) {
        return url
    }
    return null
}

private fun getSubmissionMedia(submission: SubmissionListingChildData): SubmissionMedia? {
    var url: String? = null
    var type: SubmissionMediaType? = null
    when (submission.postHint) {
        PostHint.Link -> {
            when {
                submission.domain == "i.imgur.com" && submission.url.contains(".gifv") -> {
                    url = submission.url.replace(".gifv", ".mp4")
                    type = SubmissionMediaType.Video
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
        else -> {
            // No-op
        }
    }
    if (url == null || type == null)
        return null

    val sourcePreview = submission.preview?.images?.get(0)?.source
    val width = sourcePreview?.width?.toInt() ?: submission.thumbnailWidth ?: 200
    val height = sourcePreview?.height?.toInt() ?: submission.thumbnailHeight ?: 200

    return SubmissionMedia(url = url, type = type, width = width, height = height)
}