package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import javax.inject.Inject

class TokenInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {

    suspend fun getToken() {
        networkDataSource.getUserlessToken()
    }
}