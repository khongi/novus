package com.thiosin.novus.domain.model

data class SubmissionPreview(
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String,
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
