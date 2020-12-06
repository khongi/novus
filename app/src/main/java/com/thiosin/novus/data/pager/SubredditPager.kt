package com.thiosin.novus.data.pager

import androidx.paging.PagingSource
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.network.model.Child
import com.thiosin.novus.domain.interactor.SubmissionsLister
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.domain.model.toLoadResultData

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
}