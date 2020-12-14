package com.thiosin.novus

import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
import com.thiosin.novus.screens.home.HomeReady

fun getSubmission(
    id: String = "",
    fullname: String = "",
    title: String = "",
    subreddit: String = "",
    author: String = "",
    relativeTime: String = "",
    link: String = "",
    votes: Int = 0,
    comments: Int = 0,
    selfText: String = "",
    thumbnail: String? = null,
    media: SubmissionMedia? = null,
    liked: Boolean? = null,
) = Submission(
    id = id,
    fullname = fullname,
    title = title,
    subreddit = subreddit,
    author = author,
    relativeTime = relativeTime,
    link = link,
    votes = votes,
    comments = comments,
    selfText = selfText,
    thumbnail = thumbnail,
    media = media,
    liked = liked
)

fun getSubreddit(
    queryName: String = "",
    displayName: String = "",
    type: SubredditType = SubredditType.Community,
    iconUrl: String? = null,
    iconResource: Int? = null,
): Subreddit = Subreddit(queryName, displayName, type, iconUrl, iconResource)

val builtInSubs = listOf(
    getSubreddit(queryName = "", displayName = "Frontpage", iconResource = R.drawable.ic_home),
    getSubreddit(queryName = "popular", displayName = "Popular", iconResource = R.drawable.ic_popular),
    getSubreddit(queryName = "all", displayName = "All", iconResource = R.drawable.ic_group),
)

val communitySubs = (0..9).map { getSubreddit(queryName = "subreddit$it", displayName = "subreddit$it") }

val subreddits = builtInSubs + communitySubs

val submissions = (0..9).map {
    getSubmission(
        id = "$it",
        fullname = "$it",
        title = "submission $it title",
        subreddit = "/r/${communitySubs[it].queryName}",
        author = "author1",
        relativeTime = "$it hour ago",
        votes = it,
        comments = it,
        selfText = "submission $it selftext"
    )
}

val homeReadyState = HomeReady(
    submissions = submissions,
    selectedSubreddit = builtInSubs[0],
    subreddits = subreddits,
    user = null,
    loading = false,
    voting = false
)