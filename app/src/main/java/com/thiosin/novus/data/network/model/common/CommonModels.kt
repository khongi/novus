package com.thiosin.novus.data.network.model.common

import com.squareup.moshi.Json

enum class Kind {
    @Json(name = "t1")
    Comment,

    @Json(name = "t2")
    Account,

    @Json(name = "t3")
    Link,

    @Json(name = "t4")
    Message,

    @Json(name = "t5")
    Subreddit,

    @Json(name = "t6")
    Award
}
