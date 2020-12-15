package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.subreddit.SubredditListingChildData
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse

data class Subreddit(
    val queryName: String,
    val displayName: String,
    val type: SubredditType = SubredditType.Community,
    val iconUrl: String? = null,
    val iconResource: Int? = null
)

enum class SubredditType {
    Frontpage,
    All,
    Popular,
    Community
}

fun SubredditListingResponse.toSubredditList(): List<Subreddit> {
    return data.children.map { it.data.toSubreddit() }
}

fun SubredditListingChildData.toSubreddit(): Subreddit {
    return Subreddit(
        queryName = displayName,
        displayName = "/$displayNamePrefixed",
        iconUrl = iconImg
    )
}
