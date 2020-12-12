package com.thiosin.novus.data.network.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MeResponse(
    val loid: String,

    @Json(name = "loid_created")
    val loidCreated: Long,

    val kind: String,
    val data: UserData,
)

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "is_employee")
    val isEmployee: Boolean,

    @Json(name = "has_visited_new_profile")
    val hasVisitedNewProfile: Boolean,

    @Json(name = "is_friend")
    val isFriend: Boolean,

    @Json(name = "pref_no_profanity")
    val prefNoProfanity: Boolean,

    @Json(name = "has_external_account")
    val hasExternalAccount: Boolean,

    @Json(name = "pref_geopopular")
    val prefGeopopular: String,

    @Json(name = "pref_show_trending")
    val prefShowTrending: Boolean,

    val subreddit: UserSubreddit,

    @Json(name = "has_ios_subscription")
    val hasIosSubscription: Boolean,

    @Json(name = "snoovatar_size")
    val snoovatarSize: Any? = null,

    @Json(name = "gold_expiration")
    val goldExpiration: Any? = null,

    @Json(name = "has_gold_subscription")
    val hasGoldSubscription: Boolean,

    @Json(name = "is_sponsor")
    val isSponsor: Boolean,

    @Json(name = "num_friends")
    val numFriends: Long,

    @Json(name = "can_edit_name")
    val canEditName: Boolean,

    val verified: Boolean,

    @Json(name = "new_modmail_exists")
    val newModmailExists: Any? = null,

    @Json(name = "pref_autoplay")
    val prefAutoplay: Boolean,

    val coins: Long,

    @Json(name = "has_paypal_subscription")
    val hasPaypalSubscription: Boolean,

    @Json(name = "has_subscribed_to_premium")
    val hasSubscribedToPremium: Boolean,

    val id: String,

    @Json(name = "force_password_reset")
    val forcePasswordReset: Boolean,

    @Json(name = "can_create_subreddit")
    val canCreateSubreddit: Boolean,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "is_gold")
    val isGold: Boolean,

    @Json(name = "is_mod")
    val isMod: Boolean,

    @Json(name = "awarder_karma")
    val awarderKarma: Long,

    @Json(name = "suspension_expiration_utc")
    val suspensionExpirationUTC: Any? = null,

    @Json(name = "has_verified_email")
    val hasVerifiedEmail: Boolean,

    @Json(name = "is_suspended")
    val isSuspended: Boolean,

    @Json(name = "pref_video_autoplay")
    val prefVideoAutoplay: Boolean,

    @Json(name = "has_android_subscription")
    val hasAndroidSubscription: Boolean,

    @Json(name = "in_redesign_beta")
    val inRedesignBeta: Boolean,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "has_mod_mail")
    val hasModMail: Boolean? = null,

    @Json(name = "pref_nightmode")
    val prefNightmode: Boolean,

    @Json(name = "awardee_karma")
    val awardeeKarma: Long,

    @Json(name = "hide_from_robots")
    val hideFromRobots: Boolean,

    @Json(name = "password_set")
    val passwordSet: Boolean,

    val modhash: String? = null,

    @Json(name = "link_karma")
    val linkKarma: Long,

    @Json(name = "has_stripe_subscription")
    val hasStripeSubscription: Boolean,

    @Json(name = "total_karma")
    val totalKarma: Long,

    @Json(name = "inbox_count")
    val inboxCount: Long,

    @Json(name = "pref_top_karma_subreddits")
    val prefTopKarmaSubreddits: Boolean,

    @Json(name = "has_mail")
    val hasMail: Boolean? = null,

    @Json(name = "pref_show_snoovatar")
    val prefShowSnoovatar: Boolean,

    val name: String,

    @Json(name = "pref_clickgadget")
    val prefClickgadget: Long,

    val created: Double,

    @Json(name = "gold_creddits")
    val goldCreddits: Long,

    @Json(name = "created_utc")
    val createdUTC: Double,

    @Json(name = "snoovatar_img")
    val snoovatarImg: String,

    @Json(name = "pref_show_twitter")
    val prefShowTwitter: Boolean,

    @Json(name = "in_beta")
    val inBeta: Boolean,

    @Json(name = "comment_karma")
    val commentKarma: Long,

    @Json(name = "has_subscribed")
    val hasSubscribed: Boolean,
)

@JsonClass(generateAdapter = true)
data class UserSubreddit(
    @Json(name = "default_set")
    val defaultSet: Boolean,

    @Json(name = "banner_img")
    val bannerImg: String,

    @Json(name = "restrict_posting")
    val restrictPosting: Boolean,

    @Json(name = "user_is_banned")
    val userIsBanned: Boolean,

    @Json(name = "free_form_reports")
    val freeFormReports: Boolean,

    @Json(name = "community_icon")
    val communityIcon: Any? = null,

    @Json(name = "show_media")
    val showMedia: Boolean,

    val description: String,

    @Json(name = "user_is_muted")
    val userIsMuted: Boolean,

    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "header_img")
    val headerImg: Any? = null,

    val title: String,
    val coins: Long,

    @Json(name = "previous_names")
    val previousNames: List<Any?>,

    @Json(name = "user_is_moderator")
    val userIsModerator: Boolean,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "icon_size")
    val iconSize: List<Long>,

    @Json(name = "primary_color")
    val primaryColor: String,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "icon_color")
    val iconColor: String,

    @Json(name = "submit_link_label")
    val submitLinkLabel: String,

    @Json(name = "header_size")
    val headerSize: Any? = null,

    @Json(name = "restrict_commenting")
    val restrictCommenting: Boolean,

    val subscribers: Long,

    @Json(name = "submit_text_label")
    val submitTextLabel: String,

    @Json(name = "is_default_icon")
    val isDefaultIcon: Boolean,

    @Json(name = "link_flair_position")
    val linkFlairPosition: String,

    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,

    @Json(name = "key_color")
    val keyColor: String,

    val name: String,

    @Json(name = "is_default_banner")
    val isDefaultBanner: Boolean,

    val url: String,
    val quarantine: Boolean,

    @Json(name = "banner_size")
    val bannerSize: Any? = null,

    @Json(name = "user_is_contributor")
    val userIsContributor: Boolean,

    @Json(name = "public_description")
    val publicDescription: String,

    @Json(name = "link_flair_enabled")
    val linkFlairEnabled: Boolean,

    @Json(name = "disable_contributor_requests")
    val disableContributorRequests: Boolean,

    @Json(name = "subreddit_type")
    val subredditType: String,

    @Json(name = "user_is_subscriber")
    val userIsSubscriber: Boolean,
)

