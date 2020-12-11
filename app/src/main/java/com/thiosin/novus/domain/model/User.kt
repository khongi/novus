package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.user.UserInfo

data class User(
    val name: String,
)

fun UserInfo.toUser(): User {
    return User(
        name = name
    )
}