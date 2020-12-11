package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.toSubredditList
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

    suspend fun getUserlessSubreddits(): List<Subreddit> {
        val fetchedSubreddits = networkDataSource.getUserlessSubreddits()
            ?.toSubredditList()
            ?: listOf()
        val defaultSubreddits = getDefaultSubreddits()
        return defaultSubreddits + fetchedSubreddits
    }

    suspend fun getUserSubreddits(): List<Subreddit> {
        val fetchedSubreddits = networkDataSource.getUserSubreddits()
            ?.toSubredditList()
            ?: listOf()
        val defaultSubreddits = getDefaultSubreddits()
        return defaultSubreddits + fetchedSubreddits
    }

    private fun getDefaultSubreddits(): List<Subreddit> {
        return listOf(
            Subreddit("", "Frontpage", ""),
            Subreddit("all", "All", ""),
            Subreddit("popular", "Popular", ""),
        )
    }
}