package com.thiosin.novus.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.thiosin.novus.domain.model.SubmissionPreview

@Composable
fun SubmissionPreviewItem(submission: SubmissionPreview?) {
    requireNotNull(submission)

    Card(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth()) {
        Column {
            Column(Modifier.padding(8.dp)) {
                Text(text = submission.title, style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val submission = SubmissionPreview(
        title = "This is the title"
    )
    SubmissionPreviewItem(submission = submission)
}
