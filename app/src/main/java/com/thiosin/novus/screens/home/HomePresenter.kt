package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.AuthInteractor
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.interactor.UserInteractor
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.toLoadResultData
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val subredditInteractor: SubredditInteractor,
    private val userInteractor: UserInteractor,
) {

    suspend fun acquireUserlessCredentials() = withIOContext {
        authInteractor.acquireUserlessToken()
    }

    suspend fun getSubredditPage(
        subreddit: String,
        sort: SubmissionSort = SubmissionSort.Hot,
        count: Int = 0,
        after: String = "",
    ) = withIOContext {
        val lister = subredditInteractor.getSubmissionsLister(
            subreddit = subreddit,
            sort = sort
        )

        lister.getPage(count, after)?.toLoadResultData() ?: listOf()
    }

    suspend fun getSubreddits(): List<Subreddit> = withIOContext {
        val user = userInteractor.getUser()
        if (user == null) {
            subredditInteractor.getUserlessSubreddits()
        } else {
            subredditInteractor.getUserSubreddits()
        }
    }

    suspend fun getDefaultSubreddit(): Subreddit = withIOContext {
        subredditInteractor.getDefaultSubreddit()
    }

    suspend fun getUser() = withIOContext {
        return@withIOContext userInteractor.getUser()
    }

    suspend fun logout() = withIOContext {
        userInteractor.logout()
    }
}