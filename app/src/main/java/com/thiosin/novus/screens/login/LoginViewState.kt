package com.thiosin.novus.screens.login

sealed class LoginViewState

object LoginInitial : LoginViewState()

data class LoginStart(
    val authUrl: String,
    val redirectUrl: String
) : LoginViewState()

object LoginComplete : LoginViewState()
