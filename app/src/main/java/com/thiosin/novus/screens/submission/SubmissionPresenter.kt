package com.thiosin.novus.screens.submission

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.CommentInteractor
import javax.inject.Inject

class SubmissionPresenter @Inject constructor(
    private val commentInteractor: CommentInteractor,
) {

    suspend fun getComments(submissionId: String) = withIOContext {
        commentInteractor.getComments(submissionId)
    }
}