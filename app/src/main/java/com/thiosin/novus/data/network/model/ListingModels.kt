package com.thiosin.novus.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListingResponse(
    val kind: String,
    val data: ListingData
)

@JsonClass(generateAdapter = true)
data class ListingData(
    val modhash: String,
    val dist: Long,
    val children: List<Child>,
    val after: String,
    val before: Any? = null
)

@JsonClass(generateAdapter = true)
data class Child(
    val kind: Kind,
    val data: ChildData
)

@JsonClass(generateAdapter = true)
data class ChildData(
    @Json(name = "approved_at_utc")
    val approvedAtUTC: Any? = null,

    val subreddit: String,
    val selftext: String? = null,

    @Json(name = "author_fullname")
    val authorFullname: String,

    val saved: Boolean,

    @Json(name = "mod_reason_title")
    val modReasonTitle: Any? = null,

    val gilded: Long,
    val clicked: Boolean,
    val title: String,

    @Json(name = "link_flair_richtext")
    val linkFlairRichtext: List<LinkFlairRichtext>,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String,

    val hidden: Boolean,
    val pwls: Long? = null,

    @Json(name = "link_flair_css_class")
    val linkFlairCSSClass: String? = null,

    val downs: Long,

    @Json(name = "thumbnail_height")
    val thumbnailHeight: Long? = null,

    @Json(name = "top_awarded_type")
    val topAwardedType: String? = null,

    @Json(name = "hide_score")
    val hideScore: Boolean,

    val name: String,
    val quarantine: Boolean,

    @Json(name = "link_flair_text_color")
    val linkFlairTextColor: String? = null,

    @Json(name = "upvote_ratio")
    val upvoteRatio: Double,

    @Json(name = "author_flair_background_color")
    val authorFlairBackgroundColor: String? = null,

    @Json(name = "subreddit_type")
    val subredditType: SubredditType,

    val ups: Long,

    @Json(name = "total_awards_received")
    val totalAwardsReceived: Long,

    @Json(name = "media_embed")
    val mediaEmbed: MediaEmbed,

    @Json(name = "thumbnail_width")
    val thumbnailWidth: Long? = null,

    @Json(name = "author_flair_template_id")
    val authorFlairTemplateID: String? = null,

    @Json(name = "is_original_content")
    val isOriginalContent: Boolean,

    @Json(name = "user_reports")
    val userReports: List<Any?>,

    @Json(name = "secure_media")
    val secureMedia: Media? = null,

    @Json(name = "is_reddit_media_domain")
    val isRedditMediaDomain: Boolean,

    @Json(name = "is_meta")
    val isMeta: Boolean,

    val category: Any? = null,

    @Json(name = "secure_media_embed")
    val secureMediaEmbed: MediaEmbed,

    @Json(name = "link_flair_text")
    val linkFlairText: String? = null,

    @Json(name = "can_mod_post")
    val canModPost: Boolean,

    val score: Long,

    @Json(name = "approved_by")
    val approvedBy: Any? = null,

    @Json(name = "author_premium")
    val authorPremium: Boolean,

    val thumbnail: String? = null,

    @Json(name = "author_flair_css_class")
    val authorFlairCSSClass: String? = null,

    @Json(name = "author_flair_richtext")
    val authorFlairRichtext: List<AuthorFlairRichtext>,

    val gildings: Gildings,

    @Json(name = "post_hint")
    val postHint: PostHint? = null,

    @Json(name = "content_categories")
    val contentCategories: List<String>? = null,

    @Json(name = "is_self")
    val isSelf: Boolean,

    @Json(name = "mod_note")
    val modNote: Any? = null,

    val created: Long,

    @Json(name = "link_flair_type")
    val linkFlairType: AuthorFlairType,

    val wls: Long? = null,

    @Json(name = "removed_by_category")
    val removedByCategory: Any? = null,

    @Json(name = "banned_by")
    val bannedBy: Any? = null,

    @Json(name = "author_flair_type")
    val authorFlairType: AuthorFlairType,

    val domain: String,

    @Json(name = "allow_live_comments")
    val allowLiveComments: Boolean,

    @Json(name = "selftext_html")
    val selftextHTML: String? = null,

    val likes: Any? = null,

    @Json(name = "suggested_sort")
    val suggestedSort: String? = null,

    @Json(name = "banned_at_utc")
    val bannedAtUTC: Any? = null,

    @Json(name = "url_overridden_by_dest")
    val urlOverriddenByDest: String? = null,

    @Json(name = "view_count")
    val viewCount: Any? = null,

    val archived: Boolean,

    @Json(name = "no_follow")
    val noFollow: Boolean,

    @Json(name = "is_crosspostable")
    val isCrosspostable: Boolean,

    val pinned: Boolean,

    @Json(name = "over_18")
    val over18: Boolean,

    val preview: Preview? = null,

    @Json(name = "all_awardings")
    val allAwardings: List<AllAwarding>,

    val awarders: List<Any?>,

    @Json(name = "media_only")
    val mediaOnly: Boolean,

    @Json(name = "can_gild")
    val canGild: Boolean,

    val spoiler: Boolean,
    val locked: Boolean,

    @Json(name = "author_flair_text")
    val authorFlairText: String? = null,

    @Json(name = "treatment_tags")
    val treatmentTags: List<Any?>,

    val visited: Boolean,

    @Json(name = "removed_by")
    val removedBy: Any? = null,

    @Json(name = "num_reports")
    val numReports: Any? = null,

    val distinguished: Any? = null,

    @Json(name = "subreddit_id")
    val subredditID: String,

    @Json(name = "mod_reason_by")
    val modReasonBy: Any? = null,

    @Json(name = "removal_reason")
    val removalReason: Any? = null,

    @Json(name = "link_flair_background_color")
    val linkFlairBackgroundColor: String,

    val id: String,

    @Json(name = "is_robot_indexable")
    val isRobotIndexable: Boolean,

    @Json(name = "report_reasons")
    val reportReasons: Any? = null,

    val author: String,

    @Json(name = "discussion_type")
    val discussionType: Any? = null,

    @Json(name = "num_comments")
    val numComments: Long,

    @Json(name = "send_replies")
    val sendReplies: Boolean,

    @Json(name = "whitelist_status")
    val whitelistStatus: WhitelistStatus? = null,

    @Json(name = "contest_mode")
    val contestMode: Boolean,

    @Json(name = "mod_reports")
    val modReports: List<Any?>,

    @Json(name = "author_patreon_flair")
    val authorPatreonFlair: Boolean,

    @Json(name = "author_flair_text_color")
    val authorFlairTextColor: String? = null,

    val permalink: String,

    @Json(name = "parent_whitelist_status")
    val parentWhitelistStatus: WhitelistStatus? = null,

    val stickied: Boolean,
    val url: String,

    @Json(name = "subreddit_subscribers")
    val subredditSubscribers: Long,

    @Json(name = "created_utc")
    val createdUTC: Double,

    @Json(name = "num_crossposts")
    val numCrossposts: Long,

    val media: Media? = null,

    @Json(name = "is_video")
    val isVideo: Boolean,

    @Json(name = "link_flair_template_id")
    val linkFlairTemplateID: String? = null,
)

@JsonClass(generateAdapter = true)
data class AllAwarding(
    @Json(name = "giver_coin_reward")
    val giverCoinReward: Long? = null,

    @Json(name = "subreddit_id")
    val subredditID: String? = null,

    @Json(name = "is_new")
    val isNew: Boolean,

    @Json(name = "days_of_drip_extension")
    val daysOfDripExtension: Long,

    @Json(name = "coin_price")
    val coinPrice: Long,

    val id: String,

    @Json(name = "penny_donate")
    val pennyDonate: Long? = null,

    @Json(name = "award_sub_type")
    val awardSubType: AwardSubType,

    @Json(name = "coin_reward")
    val coinReward: Long,

    @Json(name = "icon_url")
    val iconURL: String,

    @Json(name = "days_of_premium")
    val daysOfPremium: Long,

    @Json(name = "tiers_by_required_awardings")
    val tiersByRequiredAwardings: Map<String, TiersByRequiredAwarding>? = null,

    @Json(name = "resized_icons")
    val resizedIcons: List<ResizedIcon>,

    @Json(name = "icon_width")
    val iconWidth: Long,

    @Json(name = "static_icon_width")
    val staticIconWidth: Long,

    @Json(name = "start_date")
    val startDate: Long? = null,

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
    val resizedStaticIcons: List<ResizedIcon>,

    @Json(name = "icon_format")
    val iconFormat: Format? = null,

    @Json(name = "icon_height")
    val iconHeight: Long,

    @Json(name = "penny_price")
    val pennyPrice: Long? = null,

    @Json(name = "award_type")
    val awardType: AwardType,

    @Json(name = "static_icon_url")
    val staticIconURL: String
)

enum class AwardSubType {
    APPRECIATION,
    COMMUNITY,
    GLOBAL,
    GROUP,
    MODERATOR,
    PREMIUM,
}

enum class AwardType {
    community,
    global,
    moderator,
}

enum class Format {
    APNG,
    PNG,
    JPG
}

@JsonClass(generateAdapter = true)
data class ResizedIcon(
    val url: String,
    val width: Long,
    val height: Long,
    val format: Format? = null
)

@JsonClass(generateAdapter = true)
data class TiersByRequiredAwarding(
    @Json(name = "resized_icons")
    val resizedIcons: List<ResizedIcon>,

    @Json(name = "awardings_required")
    val awardingsRequired: Long,

    @Json(name = "static_icon")
    val staticIcon: ResizedIcon,

    @Json(name = "resized_static_icons")
    val resizedStaticIcons: List<ResizedIcon>,

    val icon: ResizedIcon
)

@JsonClass(generateAdapter = true)
data class AuthorFlairRichtext(
    val e: String,
    val t: String? = null,
    val a: String? = null,
    val u: String? = null
)

enum class AuthorFlairType {
    richtext,
    text,
    emoji
}

@JsonClass(generateAdapter = true)
data class Gildings(
    @Json(name = "gid_1")
    val gid1: Long? = null,

    @Json(name = "gid_2")
    val gid2: Long? = null,

    @Json(name = "gid_3")
    val gid3: Long? = null
)

@JsonClass(generateAdapter = true)
data class LinkFlairRichtext(
    val e: AuthorFlairType,
    val t: String? = null
)

@JsonClass(generateAdapter = true)
data class Media(
    @Json(name = "reddit_video")
    val redditVideo: RedditVideo? = null,

    val oembed: Oembed? = null,
    val type: String? = null
)

@JsonClass(generateAdapter = true)
data class Oembed(
    @Json(name = "provider_url")
    val providerURL: String? = null,

    val description: String? = null,
    val title: String? = null,

    @Json(name = "author_name")
    val authorName: String? = null,

    val height: Long? = null,
    val width: Long? = null,
    val html: String? = null,

    @Json(name = "thumbnail_width")
    val thumbnailWidth: Long? = null,

    val version: String? = null,

    @Json(name = "provider_name")
    val providerName: String? = null,

    @Json(name = "thumbnail_url")
    val thumbnailURL: String? = null,

    val type: String? = null,

    @Json(name = "thumbnail_height")
    val thumbnailHeight: Long? = null
)

@JsonClass(generateAdapter = true)
data class RedditVideo(
    @Json(name = "bitrate_kbps")
    val bitrateKbps: Long,

    @Json(name = "fallback_url")
    val fallbackURL: String,

    val height: Long,
    val width: Long,

    @Json(name = "scrubber_media_url")
    val scrubberMediaURL: String,

    @Json(name = "dash_url")
    val dashURL: String,

    val duration: Long,

    @Json(name = "hls_url")
    val hlsURL: String,

    @Json(name = "is_gif")
    val isGIF: Boolean,

    @Json(name = "transcoding_status")
    val transcodingStatus: TranscodingStatus
)

enum class TranscodingStatus {
    completed,
}

@JsonClass(generateAdapter = true)
data class MediaEmbed(
    val content: String? = null,
    val width: Long? = null,
    val scrolling: Boolean? = null,
    val height: Long? = null,

    @Json(name = "media_domain_url")
    val mediaDomainURL: String? = null
)

enum class WhitelistStatus {
    all_ads,
    no_ads,
    some_ads,
    promo_adult_nsfw,
    house_only
}

@JsonClass(generateAdapter = true)
data class Preview(
    val images: List<Image>,
    val enabled: Boolean,

    @Json(name = "reddit_video_preview")
    val redditVideoPreview: RedditVideo? = null
)

@JsonClass(generateAdapter = true)
data class Image(
    val source: ResizedIcon,
    val resolutions: List<ResizedIcon>,
    val variants: Variants,
    val id: String
)

@JsonClass(generateAdapter = true)
data class Variants(
    val obfuscated: Nsfw? = null,
    val nsfw: Nsfw? = null
)

@JsonClass(generateAdapter = true)
data class Nsfw(
    val source: ResizedIcon,
    val resolutions: List<ResizedIcon>
)

enum class SubredditType {
    public,
    restricted,
}

enum class Kind {
    t3,
}

enum class PostHint {
    @Json(name = "link")
    Link,

    @Json(name = "image")
    Image,

    @Json(name = "hosted:video")
    HostedVideo,

    @Json(name = "rich:video")
    RichVideo,

    @Json(name = "self")
    Self
}