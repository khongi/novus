package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.AuthInteractor
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.domain.model.toLoadResultData
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val subredditInteractor: SubredditInteractor,
    private val authInteractor: AuthInteractor
) {

    suspend fun getSubredditPage(
        subreddit: String,
        sort: SubmissionSort,
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
        subredditInteractor.getSubreddits()
    }

    suspend fun getUser() = withIOContext {
        authInteractor.getUser()
    }
}