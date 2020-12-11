package com.thiosin.novus.domain.model

import com.squareup.moshi.JsonClass
import com.thiosin.novus.data.network.model.user.UserInfo

@JsonClass(generateAdapter = true)
data class User(
    val name: String,
)

fun UserInfo.toUser(): User {
    return User(
        name = name
    )
}