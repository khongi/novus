package com.thiosin.novus.creators

import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia

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
    id,
    fullname,
    title,
    subreddit,
    author,
    relativeTime,
    link,
    votes,
    comments,
    selfText,
    thumbnail,
    media,
    liked
)