package com.thiosin.novus.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionDetails(
    submission: Submission,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        elevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(top = 4.dp)) {
            InfoRow(submission)
            TitleRow(submission)
            submission.media?.let {
                MediaRow(it, displayWidthDp)
            }
            Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Votes(submission.votes)

                LinkButton(submission.link, onLinkClick)
            }
        }
    }
}