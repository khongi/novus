package com.thiosin.novus.screens.submission

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.CommentInteractor
import com.thiosin.novus.domain.interactor.UserInteractor
import javax.inject.Inject

class SubmissionPresenter @Inject constructor(
    private val commentInteractor: CommentInteractor,
    private val userInteractor: UserInteractor,
) {

    suspend fun getComments(submissionId: String) = withIOContext {
        commentInteractor.getComments(submissionId)
    }

    suspend fun vote(fullname: String, likes: Boolean?) = withIOContext {
        userInteractor.vote(fullname, likes)
    }

    suspend fun isLoggedIn(): Boolean = withIOContext {
        userInteractor.getUser() != null
    }
}
