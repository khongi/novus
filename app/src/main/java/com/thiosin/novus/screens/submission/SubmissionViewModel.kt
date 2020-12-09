package com.thiosin.novus.screens.submission

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.thiosin.novus.domain.model.SubmissionPreview

class SubmissionViewModel @ViewModelInject constructor(
    private val submissionPresenter: SubmissionPresenter,
) : RainbowCakeViewModel<SubmissionViewState>(SubmissionInitial) {

    fun load(submissionPreview: SubmissionPreview, displayWidthDp: Float) = execute {
        viewState = SubmissionReadyState(
            submissionPreview = submissionPreview,
            displayWidthDp = displayWidthDp
        )
    }
}