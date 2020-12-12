package com.thiosin.novus.data.prefs

import android.content.Context
import com.thiosin.novus.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.moshi.moshiPref
import javax.inject.Inject

class UserInfoProvider @Inject constructor(
    @ApplicationContext context: Context,
) : SimpleKrate(context) {

    var user: User? by moshiPref("USER")
}