package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.subreddit.SubredditListingChildData
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse

data class Subreddit(
    val name: String,
    val displayName: String,
    val icon: String,
)

fun SubredditListingResponse.toSubredditList(): List<Subreddit> {
    return data.children.map { it.data.toSubreddit() }
}

fun SubredditListingChildData.toSubreddit(): Subreddit {
    return Subreddit(
        name = displayName,
        displayName = "/$displayNamePrefixed",
        icon = iconImg
    )
}