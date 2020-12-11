package com.thiosin.novus.data.network.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "is_employee")
    val isEmployee: Boolean,

    @Json(name = "seen_layout_switch")
    val seenLayoutSwitch: Boolean,

    @Json(name = "has_visited_new_profile")
    val hasVisitedNewProfile: Boolean,

    @Json(name = "pref_no_profanity")
    val prefNoProfanity: Boolean,

    @Json(name = "has_external_account")
    val hasExternalAccount: Boolean,

    @Json(name = "pref_geopopular")
    val prefGeopopular: String,

    @Json(name = "seen_redesign_modal")
    val seenRedesignModal: Boolean,

    @Json(name = "pref_show_trending")
    val prefShowTrending: Boolean,

    @Json(name = "subreddit")
    val userSubredditInfo: UserSubredditInfo,

    @Json(name = "snoovatar_img")
    val snoovatarImg: String,

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

    val features: Features,

    @Json(name = "has_android_subscription")
    val hasAndroidSubscription: Boolean,

    val verified: Boolean,

    @Json(name = "pref_autoplay")
    val prefAutoplay: Boolean,

    val coins: Long,

    @Json(name = "has_paypal_subscription")
    val hasPaypalSubscription: Boolean,

    @Json(name = "has_subscribed_to_premium")
    val hasSubscribedToPremium: Boolean,

    val id: String,

    @Json(name = "has_stripe_subscription")
    val hasStripeSubscription: Boolean,

    @Json(name = "oauth_client_id")
    val oauthClientID: String,

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

    @Json(name = "can_edit_name")
    val canEditName: Boolean,

    @Json(name = "in_redesign_beta")
    val inRedesignBeta: Boolean,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "pref_nightmode")
    val prefNightmode: Boolean,

    @Json(name = "awardee_karma")
    val awardeeKarma: Long,

    @Json(name = "hide_from_robots")
    val hideFromRobots: Boolean,

    @Json(name = "password_set")
    val passwordSet: Boolean,

    @Json(name = "link_karma")
    val linkKarma: Long,

    @Json(name = "force_password_reset")
    val forcePasswordReset: Boolean,

    @Json(name = "total_karma")
    val totalKarma: Long,

    @Json(name = "seen_give_award_tooltip")
    val seenGiveAwardTooltip: Boolean,

    @Json(name = "inbox_count")
    val inboxCount: Long,

    @Json(name = "seen_premium_adblock_modal")
    val seenPremiumAdblockModal: Boolean,

    @Json(name = "pref_top_karma_subreddits")
    val prefTopKarmaSubreddits: Boolean,

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

    @Json(name = "has_ios_subscription")
    val hasIosSubscription: Boolean,

    @Json(name = "pref_show_twitter")
    val prefShowTwitter: Boolean,

    @Json(name = "in_beta")
    val inBeta: Boolean,

    @Json(name = "comment_karma")
    val commentKarma: Long,

    @Json(name = "has_subscribed")
    val hasSubscribed: Boolean,

    @Json(name = "linked_identities")
    val linkedIdentities: List<Any?>,

    @Json(name = "seen_subreddit_chat_ftux")
    val seenSubredditChatFtux: Boolean,
)

@JsonClass(generateAdapter = true)
data class Features(
    @Json(name = "mod_service_mute_writes")
    val modServiceMuteWrites: Boolean,

    @Json(name = "promoted_trend_blanks")
    val promotedTrendBlanks: Boolean,

    @Json(name = "show_amp_link")
    val showAmpLink: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_helpdesk_reports")
    val reportServiceHandlesReportWritesToDBForHelpdeskReports: Boolean,

    @Json(name = "report_service_handles_self_harm_reports")
    val reportServiceHandlesSelfHarmReports: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_modmail_reports")
    val reportServiceHandlesReportWritesToDBForModmailReports: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_spam")
    val reportsDoubleWriteToReportServiceForSpam: Boolean,

    @Json(name = "is_email_permission_required")
    val isEmailPermissionRequired: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_modmail_reports")
    val reportsDoubleWriteToReportServiceForModmailReports: Boolean,

    @Json(name = "mod_awards")
    val modAwards: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_sendbird_chats")
    val reportServiceHandlesReportWritesToDBForSendbirdChats: Boolean,

    @Json(name = "expensive_coins_package")
    val expensiveCoinsPackage: Boolean,

    @Json(name = "mweb_xpromo_revamp_v2")
    val mwebXpromoRevampV2: MwebXpromoRevampV,

    @Json(name = "econ_wallet_service")
    val econWalletService: Boolean,

    @Json(name = "awards_on_streams")
    val awardsOnStreams: Boolean,

    @Json(name = "report_service_handles_accept_report")
    val reportServiceHandlesAcceptReport: Boolean,

    @Json(name = "mweb_xpromo_modal_listing_click_daily_dismissible_ios")
    val mwebXpromoModalListingClickDailyDismissibleIos: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_som")
    val reportsDoubleWriteToReportServiceForSom: Boolean,

    @Json(name = "chat_subreddit")
    val chatSubreddit: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_users")
    val reportsDoubleWriteToReportServiceForUsers: Boolean,

    @Json(name = "modlog_copyright_removal")
    val modlogCopyrightRemoval: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_users")
    val reportServiceHandlesReportWritesToDBForUsers: Boolean,

    @Json(name = "do_not_track")
    val doNotTrack: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db")
    val reportServiceHandlesReportWritesToDB: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_helpdesk_reports")
    val reportsDoubleWriteToReportServiceForHelpdeskReports: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_spam")
    val reportServiceHandlesReportWritesToDBForSpam: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_sendbird_chats")
    val reportsDoubleWriteToReportServiceForSendbirdChats: Boolean,

    @Json(name = "mod_service_mute_reads")
    val modServiceMuteReads: Boolean,

    @Json(name = "mweb_xpromo_interstitial_comments_ios")
    val mwebXpromoInterstitialCommentsIos: Boolean,

    @Json(name = "noreferrer_to_noopener")
    val noreferrerToNoopener: Boolean,

    @Json(name = "chat_user_settings")
    val chatUserSettings: Boolean,

    @Json(name = "premium_subscriptions_table")
    val premiumSubscriptionsTable: Boolean,

    @Json(name = "reports_double_write_to_report_service")
    val reportsDoubleWriteToReportService: Boolean,

    @Json(name = "mweb_xpromo_interstitial_comments_android")
    val mwebXpromoInterstitialCommentsAndroid: Boolean,

    @Json(name = "report_service_handles_report_writes_to_db_for_awards")
    val reportServiceHandlesReportWritesToDBForAwards: Boolean,

    @Json(name = "reports_double_write_to_report_service_for_awards")
    val reportsDoubleWriteToReportServiceForAwards: Boolean,

    @Json(name = "chat_group_rollout")
    val chatGroupRollout: Boolean,

    @Json(name = "resized_styles_images")
    val resizedStylesImages: Boolean,

    @Json(name = "spez_modal")
    val spezModal: Boolean,

    @Json(name = "mweb_xpromo_modal_listing_click_daily_dismissible_android")
    val mwebXpromoModalListingClickDailyDismissibleAndroid: Boolean,

    @Json(name = "mweb_xpromo_revamp_v3")
    val mwebXpromoRevampV3: MwebXpromoRevampV,

    @Json(name = "report_service_handles_report_writes_to_db_for_som")
    val reportServiceHandlesReportWritesToDBForSom: Boolean,
)

@JsonClass(generateAdapter = true)
data class MwebXpromoRevampV(
    val owner: String,
    val variant: String,

    @Json(name = "experiment_id")
    val experimentID: Long,
)

@JsonClass(generateAdapter = true)
data class UserSubredditInfo(
    @Json(name = "default_set")
    val defaultSet: Boolean,

    @Json(name = "user_is_contributor")
    val userIsContributor: Boolean,

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

    @Json(name = "icon_color")
    val iconColor: String,

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

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "icon_size")
    val iconSize: List<Long>,

    @Json(name = "primary_color")
    val primaryColor: String,

    @Json(name = "icon_img")
    val iconImg: String,

    val description: String,

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

    @Json(name = "user_is_moderator")
    val userIsModerator: Boolean,

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
