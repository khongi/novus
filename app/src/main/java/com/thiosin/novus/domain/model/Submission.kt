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
    val votes: String,
    val comments: Int,
    val thumbnail: String? = null,
    val media: SubmissionMedia? = null,
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
                votes = getVotesFormat(it.score),
                relativeTime = getRelativeTime(it.created.toInt()),
                thumbnail = getThumbnail(it.thumbnail),
                media = getSubmissionMedia(it),
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
    val (url: String?, type: SubmissionMediaType) = when (submission.postHint) {
        PostHint.Link -> {
            when {
                submission.domain == "i.imgur.com" && submission.url.contains(".gifv") -> {
                    val url = submission.url.replace(".gifv", ".mp4")
                    val type = SubmissionMediaType.Video
                    Pair(url, type)
                }
                else -> return null
            }
        }
        PostHint.Image -> {
            val url = submission.url
            val type = SubmissionMediaType.Image
            Pair(url, type)
        }
        PostHint.HostedVideo -> {
            // Remove end of URL until .mp4
            val url = submission.media?.redditVideo?.fallbackURL?.dropLastWhile { !it.isDigit() }
            val type = SubmissionMediaType.Video
            Pair(url, type)
        }
        else -> return null
    }

    val sourcePreview = submission.preview?.images?.get(0)?.source
    val width = sourcePreview?.width?.toInt() ?: submission.thumbnailWidth ?: 200
    val height = sourcePreview?.height?.toInt() ?: submission.thumbnailHeight ?: 200

    return url?.let { SubmissionMedia(url = it, type = type, width = width, height = height) }
}