package com.thiosin.novus.screens.submission

import com.thiosin.novus.domain.model.SubmissionPreview

sealed class SubmissionViewState

object SubmissionInitial : SubmissionViewState()

data class SubmissionReadyState(
    val submissionPreview: SubmissionPreview,
    val displayWidthDp: Float,
) : SubmissionViewState()