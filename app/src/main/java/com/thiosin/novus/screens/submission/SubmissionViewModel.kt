package com.thiosin.novus.screens.submission

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.Submission
import timber.log.Timber

class SubmissionViewModel @ViewModelInject constructor(
    private val submissionPresenter: SubmissionPresenter,
) : RainbowCakeViewModel<SubmissionViewState>(SubmissionInitial) {

    fun load(submission: Submission, displayWidthDp: Float) = execute {
        val state = SubmissionReadyState(
            submission = submission,
            comments = emptyList(),
            displayWidthDp = displayWidthDp
        )
        viewState = state

        val comments = submissionPresenter.getComments(submission.id)

        Timber.d("Got ${comments.size} root comments")

        viewState = state.copy(comments = comments)
    }
}