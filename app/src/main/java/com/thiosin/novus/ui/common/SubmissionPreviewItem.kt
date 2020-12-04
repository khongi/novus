package com.thiosin.novus.ui.common

import android.net.Uri
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
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
        } else if (submission.videoUrl != null) {
            Column {
                Spacer(modifier = Modifier.preferredHeight(8.dp))
                VideoPlayer(submission.videoUrl)
            }
        }
        Divider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun VideoPlayer(sourceUrl: String) {
    // This is the official way to access current context from Composable functions
    val context = ContextAmbient.current
    val displayMetrics = context.resources.displayMetrics

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    // We only want to react to changes in sourceUrl.
    // This callback will be execute at each commit phase if
    // [sourceUrl] has changed.
    onCommit(sourceUrl) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(
                    // Big Buck Bunny from Blender Project
                    sourceUrl
                )
            )

        exoPlayer.prepare(source)
    }

    AndroidView(viewBlock = {
        val frameLayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250F, displayMetrics).toInt(),
        )
        FrameLayout(context).apply {
            layoutParams = frameLayoutParams
            addView(
                PlayerView(context).apply {
                    useController = true
                    controllerAutoShow = false
                    player = exoPlayer
                }
            )
            exoPlayer.playWhenReady = true
        }
    })
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
