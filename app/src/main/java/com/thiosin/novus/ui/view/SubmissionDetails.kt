package com.thiosin.novus.ui.view

import androidx.compose.runtime.Composable
import com.thiosin.novus.domain.model.SubmissionPreview

@Composable
fun SubmissionDetails(
    submissionPreview: SubmissionPreview,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
) {
    SubmissionPreviewItem(
        submission = submissionPreview,
        displayWidth = displayWidthDp,
        onLinkClick = onLinkClick,
        onDetailsClick = { /*TODO*/ }
    )
}