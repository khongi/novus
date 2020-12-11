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
    private val authInteractor: AuthInteractor,
) {

    suspend fun saveUserInfo(): Boolean {
        val userInfo = networkDataSource.getUserInfo()
        if (userInfo == null) {
            Timber.e("Could not fetch user info")
            return false
        }
        userInfoProvider.user = userInfo.toUser()
        return true
    }

    fun getUser(): User? = userInfoProvider.user

    fun logout() {
        userInfoProvider.user = null
        authInteractor.logout()
    }

    suspend fun vote(id: String, likes: Boolean?) {
        val direction = when (likes) {
            false -> -1
            null -> 0
            true -> 1
        }
        networkDataSource.vote(id, direction)
    }
}