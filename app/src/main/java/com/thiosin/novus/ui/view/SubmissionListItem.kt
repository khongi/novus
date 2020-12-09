package com.thiosin.novus.ui.view

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.google.android.exoplayer2.Player
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
fun SubmissionPreviewItem(
    submission: SubmissionPreview?,
    displayWidth: Float,
    onLinkClick: (String) -> Unit,
    onDetailsClick: (SubmissionPreview) -> Unit,
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
            MediaRow(submission, displayWidth)
            ButtonRow(submission, onLinkClick, onDetailsClick)
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
private fun MediaRow(submission: SubmissionPreview, displayWidth: Float) {
    if (submission.media != null && submission.media.type != SubmissionMediaType.Thumbnail) {
        val parentWidth = displayWidth - 2 * 8
        val ratio = parentWidth / submission.media.width
        val height = submission.media.height * ratio
        Box(modifier = Modifier.width(parentWidth.dp).height(height.dp)) {
            Media(submission.media)
        }
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
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator(Modifier.size(48.dp))
            }
        },
        modifier = modifier,
        imageLoader = ImageLoader.Builder(AmbientContext.current)
            .componentRegistry {
                add(ImageDecoderDecoder())
            }
            .build()
    )
}

@Composable
fun RemoteVideo(sourceUrl: String) {
    // This is the official way to access current context from Composable functions
    val context = AmbientContext.current

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

    onDispose(callback = {
        exoPlayer.release()
    })

    AndroidView(viewBlock = {
        PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            useController = true
            controllerAutoShow = true
            player = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            exoPlayer.playWhenReady = false
        }
    })
}

@Composable
private fun ButtonRow(
    submission: SubmissionPreview,
    onLinkClicked: (String) -> Unit,
    onDetailsClick: (SubmissionPreview) -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = submission.votes,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = { onDetailsClick(submission) })
        ) {
            Spacer(Modifier.size(8.dp))
            Icon(imageVector = vectorResource(id = R.drawable.ic_outline_mode_comment_24))
            Text(text = "${submission.comments}",
                modifier = Modifier.padding(start = 4.dp),
                style = MaterialTheme.typography.caption)
            Spacer(Modifier.size(8.dp))
        }

        IconButton(onClick = { onLinkClicked(submission.link) }) {
            Icon(imageVector = vectorResource(id = R.drawable.ic_outline_link_24))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val submission = SubmissionPreview(
        fullname = "t3_jqnn6m",
        title = "Kotlin plugin updated to add data class, sealed class, annotations quicker",
        author = "VincentJoshuaET",
        subreddit = "androiddev",
        relativeTime = "1 month ago",
        votes = "205",
        comments = 9,
        link = "https://www.reddit.com/r/androiddev/comments/jqnn6m/kotlin_plugin_updated_to_add_data_class_sealed/",
        media = SubmissionMedia("https://i.redd.it/ysedmnny44y51.png",
            SubmissionMediaType.Thumbnail,
            0,
            0)
    )
    SubmissionPreviewItem(
        submission = submission,
        displayWidth = 300F,
        onLinkClick = {},
        onDetailsClick = {}
    )
}
