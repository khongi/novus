package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
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
        return networkDataSource.getUserlessSubreddits()?.toSubredditList() ?: listOf()
    }

    suspend fun getUserSubreddits(): List<Subreddit> {
        return networkDataSource.getUserSubreddits()?.toSubredditList() ?: listOf()
    }

    fun getDefaultSubreddit(): Subreddit {
        return Subreddit("", "Frontpage", SubredditType.Frontpage)
    }

    fun getBuiltInSubreddits(): List<Subreddit> {
        return listOf(
            getDefaultSubreddit(),
            Subreddit("all", "All", SubredditType.All),
            Subreddit("popular", "Popular", SubredditType.Popular),
        )
    }
}