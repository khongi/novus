package com.thiosin.novus.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class LoginViewModel @ViewModelInject constructor(
   private val loginPresenter: LoginPresenter
): RainbowCakeViewModel<LoginViewState>(LoginInitial) {

   fun startLogin() = execute {
      viewState = LoginStart(authUrl = loginPresenter.getUserAuthUrl())
   }

   fun onAuthPageStart(url: String) = executeNonBlocking {
      if (loginPresenter.isRedirectUrl(url)) {
         loginPresenter.getUserToken(url)
         viewState = LoginComplete
      }
   }
}