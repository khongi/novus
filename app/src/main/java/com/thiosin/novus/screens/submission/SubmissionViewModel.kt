package com.thiosin.novus.screens.submission

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class SubmissionViewModel @ViewModelInject constructor(
    private val submissionPresenter: SubmissionPresenter,
) : RainbowCakeViewModel<SubmissionViewState>(SubmissionInitial)