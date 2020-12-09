package com.thiosin.novus.ui.view

import androidx.compose.runtime.Composable
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionDetails(
    submission: Submission,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
) {
    SubmissionPreview(
        submission = submission,
        displayWidth = displayWidthDp,
        onLinkClick = onLinkClick,
        onDetailsClick = { /*TODO*/ }
    )
}