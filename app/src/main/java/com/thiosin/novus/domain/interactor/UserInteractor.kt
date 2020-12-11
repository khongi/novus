package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.User
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun saveUserInfo() {
        networkDataSource.getUserInfo()
        // TODO get user info from API and save it to Krate
    }

    fun getUser(): User? {
        // TODO fetch user from Krate
        return null
    }
}