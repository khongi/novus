package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.data.prefs.UserInfoProvider
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.domain.model.toUser
import timber.log.Timber
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val userInfoProvider: UserInfoProvider,
) {

    suspend fun saveUserInfo(): Boolean {
        val userInfo = networkDataSource.getUserInfo()
        if (userInfo == null) {
            Timber.e("Could not fetch user info")
            return false
        }
        val user = userInfo.toUser()
        userInfoProvider.user = user
        return true
        // TODO save it to Krate
    }

    fun getUser(): User? = userInfoProvider.user
}