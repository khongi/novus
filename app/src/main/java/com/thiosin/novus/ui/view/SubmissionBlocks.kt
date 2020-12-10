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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
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
import com.thiosin.novus.domain.model.Submission
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.domain.model.SubmissionMediaType
import com.thiosin.novus.ui.theme.redditDownVote
import com.thiosin.novus.ui.theme.redditUpvote
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlin.math.absoluteValue


@Composable
fun InfoRow(submission: Submission) {
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
fun TitleRow(submission: Submission) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (submission.media == null && submission.thumbnail != null) {
            Thumbnail(submission.thumbnail)
        }
        Title(submission.title)
    }
}

@Composable
private fun Title(title: String) {
    Text(text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
}

@Composable
fun Thumbnail(url: String) {
    RemoteImage(
        url = url,
        modifier = Modifier.size(100.dp).padding(8.dp)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MediaRow(media: SubmissionMedia, availableWidth: Dp) {
    val ratio = availableWidth.div(media.width)
    val height = ratio.times(media.height)
    Box(modifier = Modifier.width(availableWidth).height(height)) {
        Media(media = media)
    }
}

@Composable
fun Media(media: SubmissionMedia) {
    when (media.type) {
        SubmissionMediaType.Image -> {
            RemoteImage(
                url = media.url,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        SubmissionMediaType.Video -> {
            RemoteVideo(url = media.url)
        }
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
fun RemoteVideo(url: String) {
    // This is the official way to access current context from Composable functions
    val context = AmbientContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    // We only want to react to changes in sourceUrl.
    // This callback will be execute at each commit phase if
    // [sourceUrl] has changed.
    onCommit(url) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(url)
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
fun PreviewButtonRow(
    submission: Submission,
    onLinkClick: (String) -> Unit,
    onCommentsClick: (Submission) -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Votes(submission.votes)

        CommentsButton(submission, onCommentsClick)

        LinkButton(submission.link, onLinkClick)
    }
}

@Composable
fun LinkButton(
    url: String,
    onClick: (String) -> Unit,
) {
    IconButton(onClick = { onClick(url) }) {
        Icon(imageVector = vectorResource(id = R.drawable.ic_outline_link_24))
    }
}

@Composable
fun CommentsButton(
    submission: Submission,
    onClick: (Submission) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onClick(submission) })
    ) {
        Spacer(Modifier.size(8.dp))
        Icon(imageVector = vectorResource(id = R.drawable.ic_outline_mode_comment_24))
        Text(text = "${submission.comments}",
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.caption)
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
fun Votes(votes: Int) {
    Text(text = getVotesFormat(votes),
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(4.dp))
}

fun getVotesFormat(votes: Int): AnnotatedString {
    val abs = votes.absoluteValue
    val formattedValue = if (abs < 1000) {
        "$abs"
    } else {
        "%.${1}fk".format(abs / 1000.0)
    }

    return annotatedString {
        withStyle(SpanStyle(color = if (votes > 0) {
            redditUpvote
        } else {
            redditDownVote
        })) {
            append(formattedValue)
        }
    }
}