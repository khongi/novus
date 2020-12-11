package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.domain.model.toUser
import timber.log.Timber
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun saveUserInfo(): Boolean {
        val userInfo = networkDataSource.getUserInfo()
        if (userInfo == null) {
            Timber.e("Could not fetch user info")
            return false
        }
        val user = userInfo.toUser()
        return false
        // TODO save it to Krate
    }

    fun getUser(): User? {
        // TODO fetch user from Krate
        return null
    }
}