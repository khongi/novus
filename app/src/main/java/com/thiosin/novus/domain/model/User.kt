package com.thiosin.novus.domain.model

import com.squareup.moshi.JsonClass
import com.thiosin.novus.data.network.model.user.MeResponse

@JsonClass(generateAdapter = true)
data class User(
    val name: String,
)

fun MeResponse.toUser(): User {
    return User(
        name = data.name,
    )
}
