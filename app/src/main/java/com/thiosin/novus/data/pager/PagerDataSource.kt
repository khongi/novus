package com.thiosin.novus.data.pager

import androidx.paging.PagingSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagerDataSource @Inject constructor(): PagingSource<Int, String>() {

    var test = true
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val nextPage = params.key ?: 1
            val response = getResponse(nextPage)

            delay(500)

            if (test && nextPage == 3) {
                test = false
                throw Exception("Test")
            }

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1 // TODO calculate from API response
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    private fun getResponse(page: Int): List<String> {
        return (page * 100 .. (page * 100) + 19).toList().map { "$it" }
    }
}