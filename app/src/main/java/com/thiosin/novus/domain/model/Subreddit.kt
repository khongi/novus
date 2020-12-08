package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.subreddit.SubredditListingChildData

data class Subreddit(
    val name: String,
    val displayName: String,
    val icon: String,
)

fun SubredditListingChildData.toSubreddit(): Subreddit {
    return Subreddit(
        name = displayName,
        displayName = "/$displayNamePrefixed",
        icon = iconImg
    )
}