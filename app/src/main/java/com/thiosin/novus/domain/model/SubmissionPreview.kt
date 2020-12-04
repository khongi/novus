package com.thiosin.novus.domain.model

data class SubmissionPreview(
    val title: String,
    val subreddit: String,
    val author: String,
    val relativeTime: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val mediaWidth: Int? = null,
    val mediaHeight: Int? = null,
)
