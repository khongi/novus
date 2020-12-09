package com.thiosin.novus.data.network.model.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.thiosin.novus.data.network.model.common.Kind

@JsonClass(generateAdapter = true)
data class CResponse(
    val kind: String,
    val data: CResponseData,
)

@JsonClass(generateAdapter = true)
data class CResponseData(
    val modhash: String,
    val dist: Any? = null,
    val children: List<CResponseDataChild>,
    val after: Any? = null,
    val before: Any? = null,
)

@JsonClass(generateAdapter = false)
data class CResponseDataChild(
    val kind: Kind,
    val data: CommentData,
)

@JsonClass(generateAdapter = false)
data class CReplies(
    val data: List<CResponseDataChild>? = null,
)

@JsonClass(generateAdapter = true)
data class CommentData(
    @Json(name = "total_awards_received")
    val totalAwardsReceived: Long,

    val ups: Long,

    @Json(name = "link_id")
    val linkID: String,

    val replies: CReplies,

    val saved: Boolean,

    val id: String,

    val gilded: Long,

    val archived: Boolean,

    val author: String,

    @Json(name = "send_replies")
    val sendReplies: Boolean,

    @Json(name = "parent_id")
    val parentID: String,

    val score: Long,

    @Json(name = "author_fullname")
    val authorFullname: String? = null,

    @Json(name = "all_awardings")
    val allAwardings: List<AllAwarding>,

    @Json(name = "subreddit_id")
    val subredditID: String,

    val body: String,

    val downs: Long,

    @Json(name = "is_submitter")
    val isSubmitter: Boolean,

    val collapsed: Boolean,

    val gildings: Gildings,

    val stickied: Boolean,

    @Json(name = "can_gild")
    val canGild: Boolean,

    @Json(name = "score_hidden")
    val scoreHidden: Boolean,

    val permalink: String,

    val locked: Boolean,

    val name: String,

    val created: Double,

    val subreddit: String,

    @Json(name = "created_utc")
    val createdUTC: Double,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String,

    val controversiality: Long,

    val depth: Long,

    @Json(name = "author_cakeday")
    val authorCakeday: Boolean? = null,
)

@JsonClass(generateAdapter = true)
data class AllAwarding(
    @Json(name = "giver_coin_reward")
    val giverCoinReward: Long? = null,

    @Json(name = "subreddit_id")
    val subredditID: Any? = null,

    @Json(name = "is_new")
    val isNew: Boolean,

    @Json(name = "days_of_drip_extension")
    val daysOfDripExtension: Long,

    @Json(name = "coin_price")
    val coinPrice: Long,

    val id: String,

    @Json(name = "penny_donate")
    val pennyDonate: Long? = null,

    @Json(name = "coin_reward")
    val coinReward: Long,

    @Json(name = "icon_url")
    val iconURL: String,

    @Json(name = "days_of_premium")
    val daysOfPremium: Long,

    @Json(name = "icon_height")
    val iconHeight: Long,

    @Json(name = "tiers_by_required_awardings")
    val tiersByRequiredAwardings: Map<String, TiersByRequiredAwarding>? = null,

    @Json(name = "resized_icons")
    val resizedIcons: List<Icon>,

    @Json(name = "icon_width")
    val iconWidth: Long,

    @Json(name = "static_icon_width")
    val staticIconWidth: Long,

    @Json(name = "start_date")
    val startDate: Any? = null,

    @Json(name = "is_enabled")
    val isEnabled: Boolean,

    @Json(name = "awardings_required_to_grant_benefits")
    val awardingsRequiredToGrantBenefits: Long? = null,

    val description: String,

    @Json(name = "end_date")
    val endDate: Any? = null,

    @Json(name = "subreddit_coin_reward")
    val subredditCoinReward: Long,

    val count: Long,

    @Json(name = "static_icon_height")
    val staticIconHeight: Long,

    val name: String,

    @Json(name = "resized_static_icons")
    val resizedStaticIcons: List<Icon>,

    @Json(name = "icon_format")
    val iconFormat: String? = null,

    @Json(name = "award_sub_type")
    val awardSubType: AwardSubType,

    @Json(name = "penny_price")
    val pennyPrice: Long? = null,

    @Json(name = "award_type")
    val awardType: AwardType,

    @Json(name = "static_icon_url")
    val staticIconURL: String,
)

@JsonClass(generateAdapter = true)
data class Icon(
    val url: String,
    val width: Long,
    val height: Long,
    val format: String? = null,
)

@JsonClass(generateAdapter = true)
data class TiersByRequiredAwarding(
    @Json(name = "resized_static_icons")
    val resizedStaticIcons: List<Icon>,

    @Json(name = "resized_icons")
    val resizedIcons: List<Icon>,

    @Json(name = "static_icon")
    val staticIcon: Icon,

    @Json(name = "awardings_required")
    val awardingsRequired: Long,

    val icon: Icon,
)

@JsonClass(generateAdapter = true)
data class Gildings(
    @Json(name = "gid_1")
    val gid1: Long? = null,

    @Json(name = "gid_2")
    val gid2: Long? = null,
)

enum class AwardSubType {
    @Json(name = "GLOBAL")
    GLOBAL,

    @Json(name = "GROUP")
    GROUP,

    @Json(name = "APPRECIATION")
    APPRECIATION,

    @Json(name = "PREMIUM")
    PREMIUM
}

enum class AwardType {
    @Json(name = "global")
    GLOBAL
}

enum class AuthorFlairType {
    @Json(name = "text")
    TEXT
}
