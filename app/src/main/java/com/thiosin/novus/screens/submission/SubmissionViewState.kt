package com.thiosin.novus.screens.submission

import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.domain.model.Submission

sealed class SubmissionViewState

object SubmissionInitial : SubmissionViewState()

data class SubmissionReadyState(
    val submission: Submission,
    val comments: List<Comment>,
    val canVote: Boolean,
    val displayWidthDp: Float,
    val loading: Boolean = false,
    val voting: Boolean = false,
) : SubmissionViewState()
