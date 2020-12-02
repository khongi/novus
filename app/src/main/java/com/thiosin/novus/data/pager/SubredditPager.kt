package com.thiosin.novus.data.pager

import androidx.paging.PagingSource
import co.zsmb.rainbowcake.withIOContext
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.domain.model.toSubmissionPreview

class SubredditPager constructor(
    private val submissionsFetcher: SubmissionsFetcher
) : PagingSource<Int, SubmissionPreview>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, SubmissionPreview> = withIOContext {
        try {
            val nextPage = submissionsFetcher.getPageNum() + 1
            val prevPage = if (nextPage == 1) {
                null
            } else {
                nextPage - 1
            }

            val response = submissionsFetcher.fetchNext().map { it.toSubmissionPreview() }

            LoadResult.Page(
                data = response,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}