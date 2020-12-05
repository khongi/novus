package com.thiosin.novus.data.pager

import android.text.format.DateUtils
import androidx.paging.PagingSource
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.network.model.Child
import com.thiosin.novus.data.network.model.ChildData
import com.thiosin.novus.data.network.model.ListingResponse
import com.thiosin.novus.data.network.model.PostHint
import com.thiosin.novus.domain.interactor.SubmissionsLister
import com.thiosin.novus.domain.model.SubmissionMedia
import com.thiosin.novus.domain.model.SubmissionMediaType
import com.thiosin.novus.domain.model.SubmissionPreview
import timber.log.Timber

class SubredditPager constructor(
    private val submissionsLister: SubmissionsLister,
) : PagingSource<String, SubmissionPreview>() {

    private var loaded = 0

    override suspend fun load(
        params: LoadParams<String>,
    ): LoadResult<String, SubmissionPreview> = withIOContext {
        try {
            val nextKey = params.key ?: ""

            val response = submissionsLister.getPage(count = loaded, after = nextKey, limit = 25)
                ?: throw IllegalStateException("Could not fetch page ${params.key} ${params.loadSize}")

            val result = response.toLoadResultData()

            loaded += response.data.children.size

            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = getNextKey(response.data.children.last())
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    private fun getNextKey(item: Child): String {
        return "${item.kind.name}_${item.data.id}"
    }

    private fun ListingResponse.toLoadResultData(): List<SubmissionPreview> {
        val children = this.data.children
        return children.map { child ->
            child.data.let {
                SubmissionPreview(
                    title = it.title,
                    subreddit = it.subreddit,
                    author = it.author,
                    link = it.url,
                    comments = it.numComments.toInt(),
                    votes = it.ups.toVoteFormat(),
                    relativeTime = getRelativeTime(it),
                    media = getSubmissionMedia(it),
                )
            }
        }
    }

    private fun Long.toVoteFormat(): String {
        if (this < 1000) {
            return this.toString()
        }
        val k = this / 1000.0
        return "%.${1}fk".format(k)
    }

    private fun getRelativeTime(it: ChildData): String {
        return DateUtils.getRelativeTimeSpanString(
            System.currentTimeMillis(),
            it.created * 1_000L,
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }

    private fun getSubmissionMedia(submission: ChildData): SubmissionMedia? {
        Timber.d("${submission.subreddit} ${submission.author}: ${submission.url}")

        var url: String? = null
        var type = SubmissionMediaType.Thumbnail
        when (submission.postHint) {
            PostHint.Link -> {
                when {
                    submission.domain == "i.imgur.com" && submission.url.contains(".gifv") -> {
                        url = submission.url.replace(".gifv", ".mp4")
                        type = SubmissionMediaType.Video
                    }
                    submission.thumbnail != null && submission.thumbnail.startsWith("http") -> {
                        url = submission.thumbnail
                        type = SubmissionMediaType.Thumbnail
                    }
                }
            }
            PostHint.Image -> {
                url = submission.url
                type = SubmissionMediaType.Image
            }
            PostHint.HostedVideo -> {
                // Remove end of URL until .mp4
                url = submission.media?.redditVideo?.fallbackURL?.dropLastWhile { !it.isDigit() }
                type = SubmissionMediaType.Video
            }
            else -> Unit
        }

        return url?.let { SubmissionMedia(url = it, type = type) }
    }
}