package com.thiosin.novus.data.pager

import android.text.format.DateUtils
import androidx.paging.PagingSource
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.network.model.Child
import com.thiosin.novus.data.network.model.ChildData
import com.thiosin.novus.data.network.model.ListingResponse
import com.thiosin.novus.domain.interactor.SubmissionsLister
import com.thiosin.novus.domain.model.SubmissionPreview
import timber.log.Timber

class SubredditPager constructor(
    private val submissionsLister: SubmissionsLister
) : PagingSource<String, SubmissionPreview>() {

    private var loaded = 0

    override suspend fun load(
        params: LoadParams<String>
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
                    relativeTime = DateUtils.getRelativeTimeSpanString(
                        System.currentTimeMillis(),
                        it.created * 1_000L,
                        DateUtils.SECOND_IN_MILLIS
                    ).toString(),
                    imageUrl = getImageUrl(it)
                )
            }
        }
    }

    private fun getImageUrl(submission: ChildData): String? {
        Timber.d("${submission.subreddit} ${submission.author}: ${submission.url}")
        return when (submission.postHint) {
            "image" -> submission.url
            "link" -> null
            else -> null
        }
    }
}