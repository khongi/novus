package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.R
import com.thiosin.novus.domain.interactor.AuthInteractor
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.interactor.UserInteractor
import com.thiosin.novus.domain.model.SubmissionSort
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
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
        val communitySubreddits = if (user == null) {
            subredditInteractor.getUserlessSubreddits()
        } else {
            subredditInteractor.getUserSubreddits()
        }
        val buildInSubreddits = subredditInteractor.getBuiltInSubreddits().map {
            when (it.type) {
                SubredditType.Frontpage -> it.copy(iconResource = R.drawable.ic_home)
                SubredditType.All -> it.copy(iconResource = R.drawable.ic_group)
                SubredditType.Popular -> it.copy(iconResource = R.drawable.ic_popular)
                else -> it
            }
        }
        buildInSubreddits + communitySubreddits
    }

    suspend fun getDefaultSubreddit(): Subreddit = withIOContext {
        subredditInteractor.getDefaultSubreddit()
    }

    suspend fun getUser() = withIOContext {
        userInteractor.getUser()
    }

    suspend fun logout() = withIOContext {
        userInteractor.logout()
    }

    suspend fun vote(fullname: String, likes: Boolean?) = withIOContext {
        userInteractor.vote(fullname, likes)
    }
}