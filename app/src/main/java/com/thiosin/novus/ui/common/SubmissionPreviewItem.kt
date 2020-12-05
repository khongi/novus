package com.thiosin.novus.ui.common

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.loadVectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.domain.model.SubmissionMediaType
import com.thiosin.novus.domain.model.SubmissionPreview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun SubmissionPreviewItem(submission: SubmissionPreview?, onLinkClicked: (String) -> Unit) {
    requireNotNull(submission)

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        elevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(top = 4.dp)) {
            InfoRow(submission)
            TitleRow(submission)
            MediaRow(submission)
            ButtonRow(submission, onLinkClicked)
        }
    }
}

@Composable
private fun InfoRow(submission: SubmissionPreview) {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = submission.subreddit,
            color = MaterialTheme.colors.secondary,
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
private fun TitleRow(
    submission: SubmissionPreview,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (submission.media != null && submission.media.type == SubmissionMediaType.Thumbnail
        ) {
            RemoteImage(
                url = submission.media.url,
                modifier = Modifier.size(100.dp).padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = submission.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))

    }
}

@Composable
private fun MediaRow(submission: SubmissionPreview) {
    if (submission.media != null && submission.media.type != SubmissionMediaType.Thumbnail) {
        Media(submission.media)
    }
}

@Composable
private fun Media(media: SubmissionMedia) {
    when (media.type) {
        SubmissionMediaType.Image -> {
            RemoteImage(
                url = media.url,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        SubmissionMediaType.Video -> {
            RemoteVideo(media.url)
        }
        else -> Unit
    }
}

@Composable
fun RemoteImage(url: String, modifier: Modifier, contentScale: ContentScale) {
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
fun RemoteVideo(sourceUrl: String) {
    // This is the official way to access current context from Composable functions
    val context = ContextAmbient.current

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
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
            useController = true
            controllerAutoShow = false
            player = exoPlayer
            exoPlayer.playWhenReady = true
        }
    })
}

@Composable
private fun ButtonRow(
    submission: SubmissionPreview,
    onLinkClicked: (String) -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = submission.votes,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            loadVectorResource(id = R.drawable.ic_outline_mode_comment_24).resource.resource?.let {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(asset = it)
                }
            }
            Text(text = "${submission.comments}",
                style = MaterialTheme.typography.caption)
        }

        loadVectorResource(id = R.drawable.ic_outline_link_24).resource.resource?.let {
            IconButton(onClick = { onLinkClicked(submission.link) }) {
                Icon(asset = it)
            }
        }
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
        votes = "64.1k",
        comments = 123,
        link = ""
    )
    SubmissionPreviewItem(submission = submission, onLinkClicked = {})
}
