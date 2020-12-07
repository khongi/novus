package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.subreddit.ChildData

data class Subreddit(
    val name: String,
    val displayName: String,
    val icon: String,
)

fun ChildData.toSubreddit(): Subreddit {
    return Subreddit(
        name = displayName,
        displayName = "/$displayNamePrefixed",
        icon = iconImg
    )
}