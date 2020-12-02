package com.thiosin.novus.ui.login

sealed class LoginViewState

object LoginInitial : LoginViewState()

data class LoginStart(val authUrl: String) : LoginViewState()

object LoginComplete : LoginViewState()