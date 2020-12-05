package com.thiosin.novus.ui.common

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
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
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.domain.model.SubmissionMediaType
import com.thiosin.novus.domain.model.SubmissionPreview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun SubmissionPreviewItem(submission: SubmissionPreview?) {
    requireNotNull(submission)

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
        SubtitleRow(submission)
        Row(modifier = Modifier.fillMaxWidth()) {
            if (submission.media != null
                && submission.media.type == SubmissionMediaType.Thumbnail
            ) {
                Thumbnail(submission.media)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Title(submission.title)
                if (submission.media != null) {
                    // TODO remove - for debug purposes
                    Text(
                        text = "${submission.media.type} ${submission.media.url} ${submission.media.width} ${submission.media.height}",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Media(submission.media)
                }
            }
        }
    }
}

@Composable
private fun Media(media: SubmissionMedia) {
    when (media.type) {
        SubmissionMediaType.Image -> {
            Image(
                url = media.url,
                modifier = Modifier.fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.FillWidth
            )
        }
        SubmissionMediaType.Video -> {
            Video(media.url,
                media.height,
                media.width)
        }
        else -> Unit
    }
}

@Composable
private fun Title(title: String) {
    Text(text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
private fun Thumbnail(media: SubmissionMedia) {
    Image(
        url = media.url,
        modifier = Modifier.size(100.dp).padding(top = 8.dp, end = 8.dp)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun SubtitleRow(submission: SubmissionPreview) {
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
}

@Composable
fun Image(url: String, modifier: Modifier, contentScale: ContentScale) {
    CoilImage(
        data = url,
        fadeIn = true,
        contentScale = contentScale,
        loading = {
            Box(modifier = Modifier.fillMaxSize()) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }
        },
        modifier = modifier,
        imageLoader = ImageLoader.Builder(ContextAmbient.current)
            .componentRegistry {
                add(ImageDecoderDecoder())
            }
            .build()
    )
}

@Composable
fun Video(sourceUrl: String, mediaHeightPx: Int, mediaWidthPx: Int) {
    // This is the official way to access current context from Composable functions
    val context = ContextAmbient.current
    val screenWidthPx = remember { context.resources.displayMetrics.widthPixels }

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
                Uri.parse(sourceUrl)
            )

        exoPlayer.prepare(source)
    }

    AndroidView(viewBlock = {
        PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getCalculatedMediaHeight(screenWidthPx, mediaWidthPx, mediaHeightPx),
            )
            useController = true
            controllerAutoShow = false
            player = exoPlayer
            exoPlayer.playWhenReady = true
        }
    })
}

private fun getCalculatedMediaHeight(displayWidth: Int, width: Int, height: Int): Int {
    val ratio = displayWidth / width.toFloat()
    return (ratio * height).toInt()
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
