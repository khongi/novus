package com.thiosin.novus.screens.login

import co.zsmb.rainbowcake.withIOContext
import com.thiosin.novus.domain.interactor.AuthInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) {
    suspend fun acquireUserlessToken() = withIOContext {
        authInteractor.acquireUserlessToken()
    }

    suspend fun getUserAuthUrl(): String = withIOContext {
        authInteractor.getUserAuthUrl()
    }

    suspend fun isRedirectUrl(url: String): Boolean = withIOContext {
        authInteractor.isUserRedirectUrl(url)
    }

    suspend fun getUserToken(url: String) = withIOContext {
        authInteractor.acquireUserToken(url)
    }
}
