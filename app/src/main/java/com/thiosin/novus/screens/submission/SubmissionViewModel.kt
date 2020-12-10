package com.thiosin.novus.screens.submission

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.Submission

class SubmissionViewModel @ViewModelInject constructor(
    private val submissionPresenter: SubmissionPresenter,
) : RainbowCakeViewModel<SubmissionViewState>(SubmissionInitial) {

    fun load(submission: Submission, displayWidthDp: Float) = execute {
        val readyState = viewState as? SubmissionReadyState

        val loadingWithContentState = readyState?.copy(loading = true)
            ?: SubmissionReadyState(
                submission = submission,
                comments = emptyList(),
                displayWidthDp = displayWidthDp,
                loading = true
            )
        viewState = loadingWithContentState

        val comments = submissionPresenter.getComments(submission.id)

        viewState = loadingWithContentState.copy(comments = comments, loading = false)
    }
}