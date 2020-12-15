package com.thiosin.novus.data.auth

import android.content.Context
import com.kirkbushman.auth.managers.StorageManager
import com.kirkbushman.auth.models.AuthType
import com.kirkbushman.auth.models.Token
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import hu.autsoft.krate.*
import hu.autsoft.krate.moshi.moshiPref
import javax.inject.Inject

@ActivityScoped
class AuthInfoProvider @Inject constructor(
    @ApplicationContext context: Context,
) : SimpleKrate(context), StorageManager {

    private var accessToken by stringPref("ACCESS_TOKEN", "")
    private var refreshToken by stringPref("REFRESH_TOKEN")
    private var expiresInSec by intPref("EXPIRES_IN", 0)
    private var createdTime by longPref("CREATED_TIME", 0L)
    private var tokenType by stringPref("TOKEN_TYPE", "")
    private var scopes by stringPref("SCOPES", "")
    private var authType by moshiPref("AUTH_TYPE", AuthType.NONE)

    var loggedIn: Boolean by booleanPref("LOGGED_IN", false)

    override fun authType(): AuthType = authType

    override fun clearAll() {
        accessToken = ""
        refreshToken = null
        expiresInSec = 0
        createdTime = 0L
        tokenType = ""
        scopes = ""
        authType = AuthType.NONE
        loggedIn = false
    }

    override fun getToken(): Token {
        if (hasToken().not()) {
            throw IllegalStateException("Token is missing")
        }
        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
            tokenType = tokenType,
            expiresInSecs = expiresInSec,
            createdTime = createdTime,
            scopes = scopes
        )
    }

    override fun hasToken(): Boolean {
        val refreshTokenStatus = if (authType == AuthType.INSTALLED_APP) {
            refreshToken != null
        } else {
            true
        }

        return accessToken.isNotBlank() && refreshTokenStatus
    }

    override fun isAuthed(): Boolean {
        return authType != AuthType.NONE
    }

    override fun saveToken(token: Token, authType: AuthType) {
        this.authType = authType
        accessToken = token.accessToken
        refreshToken = token.refreshToken
        tokenType = token.tokenType
        expiresInSec = token.expiresInSecs
        createdTime = token.createdTime
        scopes = token.scopes
    }
}
