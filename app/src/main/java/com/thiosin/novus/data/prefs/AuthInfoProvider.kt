package com.thiosin.novus.data.prefs

import android.content.Context
import com.kirkbushman.auth.managers.StorageManager
import com.kirkbushman.auth.models.AuthType
import com.kirkbushman.auth.models.Token
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.intPref
import hu.autsoft.krate.longPref
import hu.autsoft.krate.moshi.moshiPref
import hu.autsoft.krate.stringPref
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

    var modhash by stringPref("MODHASH")

    override fun authType(): AuthType = authType

    override fun clearAll() {
        accessToken = ""
        refreshToken = null
        expiresInSec = 0
        createdTime = 0L
        tokenType = ""
        scopes = ""
        authType = AuthType.NONE
        modhash = null
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

        return accessToken.isNotBlank()
                && refreshTokenStatus
                && expiresInSec != 0
                && createdTime != 0L
                && scopes.isNotBlank()
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