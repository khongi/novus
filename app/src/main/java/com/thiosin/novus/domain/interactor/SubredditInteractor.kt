package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.*
import javax.inject.Inject

class SubredditInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    fun getSubmissionsLister(
        subreddit: String,
        sort: SubmissionSort = SubmissionSort.Hot,
    ): SubmissionsLister {
        return SubmissionsLister(
            subreddit = subreddit,
            sort = sort,
            networkDataSource = networkDataSource,
        )
    }

    suspend fun getSubreddits(): List<Subreddit> {
        val fetchedSubreddits = networkDataSource.getUserlessSubreddits()
            ?.data?.children?.map { it.data.toSubreddit() }
            ?: listOf()
        val defaultSubreddits = getDefaultSubreddits()
        return defaultSubreddits + fetchedSubreddits
    }

    suspend fun getComments(submissionId: String): List<Comment> {
        val commentsResult = networkDataSource.getComments(submissionId)
        return commentsResult.toCommentList()
    }

    private fun getDefaultSubreddits(): List<Subreddit> {
        return listOf(
            Subreddit("", "Frontpage", ""),
            Subreddit("all", "All", ""),
            Subreddit("popular", "Popular", ""),
        )
    }
}