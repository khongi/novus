package com.thiosin.novus.screens.login

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class LoginViewModel @ViewModelInject constructor(
    private val loginPresenter: LoginPresenter,
) : RainbowCakeViewModel<LoginViewState>(LoginInitial) {

    fun startLogin() = execute {
        viewState = LoginStart(
            authUrl = loginPresenter.getUserAuthUrl(),
            redirectUrl = loginPresenter.getRedirectUrl()
        )
    }

    fun onPageStart(url: String) = executeNonBlocking {
        if (loginPresenter.isRedirectUrl(url)) {
            val userToken = loginPresenter.getUserToken(url)

            loginPresenter.setLoggedInStatus(userToken != null)

            if (userToken != null) {
                val userIsSaved = loginPresenter.saveUserInfo()
                if (userIsSaved) {
                    viewState = LoginComplete
                }
            }
        }
    }
}
