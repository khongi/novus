package com.thiosin.novus.ui.view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.Submission

@Composable
fun SubmissionPreview(
    submission: Submission?,
    displayWidthDp: Float,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (Submission) -> Unit,
) {
    requireNotNull(submission)

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        elevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(top = 4.dp)) {
            InfoRow(submission)
            TitleRow(submission)
            submission.media?.let {
                // Screen width - Horizontal padding
                MediaRow(it, displayWidthDp.dp - 16.dp)
            }
            PreviewButtonRow(submission, onLinkClick, onDetailsClick)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val submission = Submission(
        id = "jqnn6m",
        fullname = "t3_jqnn6m",
        title = "Kotlin plugin updated to add data class, sealed class, annotations quicker",
        author = "VincentJoshuaET",
        subreddit = "androiddev",
        relativeTime = "1 month ago",
        votes = "205",
        comments = 9,
        link = "https://www.reddit.com/r/androiddev/comments/jqnn6m/kotlin_plugin_updated_to_add_data_class_sealed/",
        thumbnail = ""
    )
    SubmissionPreview(
        submission = submission,
        displayWidthDp = 300F,
        onLinkClick = {},
        onDetailsClick = {}
    )
}

fun Context.getDisplayWidthDp(): Float {
    val displayMetrics = resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}
