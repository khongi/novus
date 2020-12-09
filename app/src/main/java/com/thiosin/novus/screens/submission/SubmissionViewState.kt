package com.thiosin.novus.screens.submission

import com.thiosin.novus.domain.model.Submission

sealed class SubmissionViewState

object SubmissionInitial : SubmissionViewState()

data class SubmissionReadyState(
    val submission: Submission,
    val displayWidthDp: Float,
) : SubmissionViewState()