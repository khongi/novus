package com.thiosin.novus.screens.login

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class LoginViewModel @ViewModelInject constructor(
   private val loginPresenter: LoginPresenter
): RainbowCakeViewModel<LoginViewState>(LoginInitial) {

   fun startLogin() = execute {
//      loginPresenter.acquireUserlessToken()

      viewState = LoginStart(
         authUrl = loginPresenter.getUserAuthUrl(),
         redirectUrl = loginPresenter.getRedirectUrl()
      )
   }

   fun onPageStart(url: String) = executeNonBlocking {
      if (loginPresenter.isRedirectUrl(url)) {
         loginPresenter.getUserToken(url)
         // Get user info?
         viewState = LoginComplete
      }
   }
}