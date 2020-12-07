package com.thiosin.novus.data.network.model.subreddit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.thiosin.novus.data.network.model.common.Kind

@JsonClass(generateAdapter = true)
data class SubredditListingResponse(
    val kind: String,
    val data: SubredditListingData,
)

@JsonClass(generateAdapter = true)
data class SubredditListingData(
    val modhash: String,
    val dist: Long,
    val children: List<SubredditListingChild>,
    val after: String,
    val before: Any? = null,
)

@JsonClass(generateAdapter = true)
data class SubredditListingChild(
    val kind: Kind,
    val data: SubredditListingChildData,
)

@JsonClass(generateAdapter = true)
data class SubredditListingChildData(
    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "header_img")
    val headerImg: String? = null,

    val title: String,

    @Json(name = "icon_size")
    val iconSize: List<Long>? = null,

    @Json(name = "primary_color")
    val primaryColor: String,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,

    val subscribers: Long,

    val name: String,

    @Json(name = "public_description")
    val publicDescription: String,

    @Json(name = "community_icon")
    val communityIcon: String,

    @Json(name = "submit_text")
    val submitText: String,

    @Json(name = "key_color")
    val keyColor: String,

    val id: String,

    val over18: Boolean,

    val description: String,

    @Json(name = "description_html")
    val descriptionHtml: String,

    val url: String,
)
