package com.thiosin.novus.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.data.pager.PagerDataSource
import com.thiosin.novus.domain.interactor.TokenInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val pagerDataSource: PagerDataSource,
    private val tokenInteractor: TokenInteractor
) {

    fun getList(): Flow<PagingData<String>> {
        // TODO configure paging config
        return Pager(PagingConfig(20)) {
            pagerDataSource
        }.flow
    }

    suspend fun getToken() = withIOContext {
        tokenInteractor.getToken()
    }
}