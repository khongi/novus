package com.thiosin.novus.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.thiosin.novus.domain.model.SubmissionPreview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun SubmissionPreviewItem(submission: SubmissionPreview?) {
    requireNotNull(submission)

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
        Row {
            Text(
                text = submission.subreddit,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2,
            )
            Text(
                text = submission.author,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.subtitle2,
            )
            Text(
                text = submission.relativeTime,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.subtitle2,
            )
        }
        if (submission.imageUrl != null) {
            Text(
                text = "${submission.imageUrl}",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.subtitle2,
            )
        }
        Text(text = submission.title, style = MaterialTheme.typography.h6)
        if (submission.imageUrl != null) {
            Column {
                Spacer(modifier = Modifier.preferredHeight(8.dp))
                CoilImage(
                    data = submission.imageUrl,
                    fadeIn = true,
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium),
                    imageLoader = ImageLoader.Builder(ContextAmbient.current)
                        .componentRegistry {
                            add(ImageDecoderDecoder())
                        }
                        .build()
                )
            }
        }
        Divider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val submission = SubmissionPreview(
        title = "This is the title",
        author = "thiosin",
        subreddit = "linux",
        relativeTime = "6h",
    )
    SubmissionPreviewItem(submission = submission)
}
