package com.thiosin.novus.data.network.json

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import com.thiosin.novus.data.network.model.comment.*
import java.lang.reflect.Constructor
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String

@Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION", "RedundantExplicitType",
    "LocalVariableName")
class CommentDataAdapter(
    moshi: Moshi,
    private val cRepliesAdapter: JsonAdapter<CReplies>,
) : JsonAdapter<CommentData>() {
    private val options: JsonReader.Options = JsonReader.Options.of("total_awards_received",
        "ups",
        "link_id",
        "replies",
        "saved",
        "id",
        "gilded",
        "archived",
        "author",
        "send_replies",
        "parent_id",
        "score",
        "author_fullname",
        "all_awardings",
        "subreddit_id",
        "body",
        "downs",
        "is_submitter",
        "collapsed",
        "gildings",
        "stickied",
        "can_gild",
        "score_hidden",
        "permalink",
        "locked",
        "name",
        "created",
        "subreddit",
        "created_utc",
        "subreddit_name_prefixed",
        "controversiality",
        "depth",
        "author_cakeday",
        "distinguished")

    private val longAdapter: JsonAdapter<Long> = moshi.adapter(Long::class.java, emptySet(),
        "totalAwardsReceived")

    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
        "linkID")

    private val booleanAdapter: JsonAdapter<Boolean> =
        moshi.adapter(Boolean::class.java, emptySet(),
            "saved")

    private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java,
        emptySet(), "authorFullname")

    private val listOfAllAwardingAdapter: JsonAdapter<List<AllAwarding>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, AllAwarding::class.java),
            emptySet(), "allAwardings")

    private val gildingsAdapter: JsonAdapter<Gildings> = moshi.adapter(Gildings::class.java,
        emptySet(), "gildings")

    private val doubleAdapter: JsonAdapter<Double> = moshi.adapter(Double::class.java, emptySet(),
        "created")

    private val nullableBooleanAdapter: JsonAdapter<Boolean?> =
        moshi.adapter(Boolean::class.javaObjectType, emptySet(), "authorCakeday")

    private val nullableDistinguishedAdapter: JsonAdapter<Distinguished?> =
        moshi.adapter(Distinguished::class.java, emptySet(), "distinguished")

    @Volatile
    private var constructorRef: Constructor<CommentData>? = null

    override fun toString(): String = buildString(33) {
        append("GeneratedJsonAdapter(").append("CommentData").append(')')
    }

    override fun fromJson(reader: JsonReader): CommentData {
        var totalAwardsReceived: Long? = null
        var ups: Long? = null
        var linkID: String? = null
        var replies: CReplies? = null
        var saved: Boolean? = null
        var id: String? = null
        var gilded: Long? = null
        var archived: Boolean? = null
        var author: String? = null
        var sendReplies: Boolean? = null
        var parentID: String? = null
        var score: Long? = null
        var authorFullname: String? = null
        var allAwardings: List<AllAwarding>? = null
        var subredditID: String? = null
        var body: String? = null
        var downs: Long? = null
        var isSubmitter: Boolean? = null
        var collapsed: Boolean? = null
        var gildings: Gildings? = null
        var stickied: Boolean? = null
        var canGild: Boolean? = null
        var scoreHidden: Boolean? = null
        var permalink: String? = null
        var locked: Boolean? = null
        var name: String? = null
        var created: Double? = null
        var subreddit: String? = null
        var createdUTC: Double? = null
        var subredditNamePrefixed: String? = null
        var controversiality: Long? = null
        var depth: Long? = null
        var authorCakeday: Boolean? = null
        var distinguished: Distinguished? = null
        var mask0 = -1
        var mask1 = -1
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> totalAwardsReceived =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("totalAwardsReceived",
                        "total_awards_received",
                        reader)
                1 -> ups =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("ups", "ups", reader)
                2 -> linkID = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("linkID",
                    "link_id", reader)
                3 -> replies =
                    cRepliesAdapter.fromJson(reader) ?: throw Util.unexpectedNull("replies",
                        "replies", reader)
                4 -> saved =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("saved", "saved",
                        reader)
                5 -> id =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("id", "id", reader)
                6 -> gilded =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("gilded", "gilded",
                        reader)
                7 -> archived =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("archived",
                        "archived", reader)
                8 -> author = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("author",
                    "author", reader)
                9 -> sendReplies =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("sendReplies",
                        "send_replies",
                        reader)
                10 -> parentID =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("parentID",
                        "parent_id", reader)
                11 -> score =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("score", "score",
                        reader)
                12 -> {
                    authorFullname = nullableStringAdapter.fromJson(reader)
                    // $mask = $mask and (1 shl 12).inv()
                    mask0 = mask0 and 0xffffefff.toInt()
                }
                13 -> allAwardings = listOfAllAwardingAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("allAwardings", "all_awardings", reader)
                14 -> subredditID =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("subredditID",
                        "subreddit_id",
                        reader)
                15 -> body =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("body", "body",
                        reader)
                16 -> downs =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("downs", "downs",
                        reader)
                17 -> isSubmitter =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("isSubmitter",
                        "is_submitter",
                        reader)
                18 -> collapsed =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("collapsed",
                        "collapsed", reader)
                19 -> gildings =
                    gildingsAdapter.fromJson(reader) ?: throw Util.unexpectedNull("gildings",
                        "gildings", reader)
                20 -> stickied =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("stickied",
                        "stickied", reader)
                21 -> canGild =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("canGild",
                        "can_gild", reader)
                22 -> scoreHidden =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("scoreHidden",
                        "score_hidden",
                        reader)
                23 -> permalink =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("permalink",
                        "permalink", reader)
                24 -> locked =
                    booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull("locked",
                        "locked", reader)
                25 -> name =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("name", "name",
                        reader)
                26 -> created =
                    doubleAdapter.fromJson(reader) ?: throw Util.unexpectedNull("created",
                        "created", reader)
                27 -> subreddit =
                    stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("subreddit",
                        "subreddit", reader)
                28 -> createdUTC =
                    doubleAdapter.fromJson(reader) ?: throw Util.unexpectedNull("createdUTC",
                        "created_utc", reader)
                29 -> subredditNamePrefixed = stringAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("subredditNamePrefixed",
                        "subreddit_name_prefixed",
                        reader)
                30 -> controversiality =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("controversiality",
                        "controversiality",
                        reader)
                31 -> depth =
                    longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("depth", "depth",
                        reader)
                32 -> {
                    authorCakeday = nullableBooleanAdapter.fromJson(reader)
                    // $mask = $mask and (1 shl 0).inv()
                    mask1 = mask1 and 0xfffffffe.toInt()
                }
                33 -> {
                    distinguished = nullableDistinguishedAdapter.fromJson(reader)
                    // $mask = $mask and (1 shl 1).inv()
                    mask1 = mask1 and 0xfffffffd.toInt()
                }
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()
        @Suppress("UNCHECKED_CAST")
        val localConstructor: Constructor<CommentData> = this.constructorRef
            ?: CommentData::class.java.getDeclaredConstructor(Long::class.javaPrimitiveType,
                Long::class.javaPrimitiveType,
                String::class.java,
                CReplies::class.java,
                Boolean::class.javaPrimitiveType,
                String::class.java,
                Long::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                String::class.java,
                Boolean::class.javaPrimitiveType,
                String::class.java,
                Long::class.javaPrimitiveType,
                String::class.java,
                List::class.java,
                String::class.java,
                String::class.java,
                Long::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                Gildings::class.java,
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                String::class.java,
                Boolean::class.javaPrimitiveType,
                String::class.java,
                Double::class.javaPrimitiveType,
                String::class.java,
                Double::class.javaPrimitiveType,
                String::class.java,
                Long::class.javaPrimitiveType,
                Long::class.javaPrimitiveType,
                Boolean::class.javaObjectType,
                Distinguished::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                Util.DEFAULT_CONSTRUCTOR_MARKER).also {
                this.constructorRef = it
            }
        return localConstructor.newInstance(
            totalAwardsReceived ?: throw Util.missingProperty("totalAwardsReceived",
                "total_awards_received", reader),
            ups ?: throw Util.missingProperty("ups", "ups", reader),
            linkID ?: throw Util.missingProperty("linkID", "link_id", reader),
            replies ?: throw Util.missingProperty("replies", "replies", reader),
            saved ?: throw Util.missingProperty("saved", "saved", reader),
            id ?: throw Util.missingProperty("id", "id", reader),
            gilded ?: throw Util.missingProperty("gilded", "gilded", reader),
            archived ?: throw Util.missingProperty("archived", "archived", reader),
            author ?: throw Util.missingProperty("author", "author", reader),
            sendReplies ?: throw Util.missingProperty("sendReplies", "send_replies", reader),
            parentID ?: throw Util.missingProperty("parentID", "parent_id", reader),
            score ?: throw Util.missingProperty("score", "score", reader),
            authorFullname,
            allAwardings ?: throw Util.missingProperty("allAwardings", "all_awardings", reader),
            subredditID ?: throw Util.missingProperty("subredditID", "subreddit_id", reader),
            body ?: throw Util.missingProperty("body", "body", reader),
            downs ?: throw Util.missingProperty("downs", "downs", reader),
            isSubmitter ?: throw Util.missingProperty("isSubmitter", "is_submitter", reader),
            collapsed ?: throw Util.missingProperty("collapsed", "collapsed", reader),
            gildings ?: throw Util.missingProperty("gildings", "gildings", reader),
            stickied ?: throw Util.missingProperty("stickied", "stickied", reader),
            canGild ?: throw Util.missingProperty("canGild", "can_gild", reader),
            scoreHidden ?: throw Util.missingProperty("scoreHidden", "score_hidden", reader),
            permalink ?: throw Util.missingProperty("permalink", "permalink", reader),
            locked ?: throw Util.missingProperty("locked", "locked", reader),
            name ?: throw Util.missingProperty("name", "name", reader),
            created ?: throw Util.missingProperty("created", "created", reader),
            subreddit ?: throw Util.missingProperty("subreddit", "subreddit", reader),
            createdUTC ?: throw Util.missingProperty("createdUTC", "created_utc", reader),
            subredditNamePrefixed ?: throw Util.missingProperty("subredditNamePrefixed",
                "subreddit_name_prefixed", reader),
            controversiality ?: throw Util.missingProperty("controversiality", "controversiality",
                reader),
            depth ?: throw Util.missingProperty("depth", "depth", reader),
            authorCakeday,
            distinguished,
            mask0, mask1,
            null
        )
    }

    override fun toJson(writer: JsonWriter, value: CommentData?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
        writer.beginObject()
        writer.name("total_awards_received")
        longAdapter.toJson(writer, value.totalAwardsReceived)
        writer.name("ups")
        longAdapter.toJson(writer, value.ups)
        writer.name("link_id")
        stringAdapter.toJson(writer, value.linkID)
        writer.name("replies")
        cRepliesAdapter.toJson(writer, value.replies)
        writer.name("saved")
        booleanAdapter.toJson(writer, value.saved)
        writer.name("id")
        stringAdapter.toJson(writer, value.id)
        writer.name("gilded")
        longAdapter.toJson(writer, value.gilded)
        writer.name("archived")
        booleanAdapter.toJson(writer, value.archived)
        writer.name("author")
        stringAdapter.toJson(writer, value.author)
        writer.name("send_replies")
        booleanAdapter.toJson(writer, value.sendReplies)
        writer.name("parent_id")
        stringAdapter.toJson(writer, value.parentID)
        writer.name("score")
        longAdapter.toJson(writer, value.score)
        writer.name("author_fullname")
        nullableStringAdapter.toJson(writer, value.authorFullname)
        writer.name("all_awardings")
        listOfAllAwardingAdapter.toJson(writer, value.allAwardings)
        writer.name("subreddit_id")
        stringAdapter.toJson(writer, value.subredditID)
        writer.name("body")
        stringAdapter.toJson(writer, value.body)
        writer.name("downs")
        longAdapter.toJson(writer, value.downs)
        writer.name("is_submitter")
        booleanAdapter.toJson(writer, value.isSubmitter)
        writer.name("collapsed")
        booleanAdapter.toJson(writer, value.collapsed)
        writer.name("gildings")
        gildingsAdapter.toJson(writer, value.gildings)
        writer.name("stickied")
        booleanAdapter.toJson(writer, value.stickied)
        writer.name("can_gild")
        booleanAdapter.toJson(writer, value.canGild)
        writer.name("score_hidden")
        booleanAdapter.toJson(writer, value.scoreHidden)
        writer.name("permalink")
        stringAdapter.toJson(writer, value.permalink)
        writer.name("locked")
        booleanAdapter.toJson(writer, value.locked)
        writer.name("name")
        stringAdapter.toJson(writer, value.name)
        writer.name("created")
        doubleAdapter.toJson(writer, value.created)
        writer.name("subreddit")
        stringAdapter.toJson(writer, value.subreddit)
        writer.name("created_utc")
        doubleAdapter.toJson(writer, value.createdUTC)
        writer.name("subreddit_name_prefixed")
        stringAdapter.toJson(writer, value.subredditNamePrefixed)
        writer.name("controversiality")
        longAdapter.toJson(writer, value.controversiality)
        writer.name("depth")
        longAdapter.toJson(writer, value.depth)
        writer.name("author_cakeday")
        nullableBooleanAdapter.toJson(writer, value.authorCakeday)
        writer.name("distinguished")
        nullableDistinguishedAdapter.toJson(writer, value.distinguished)
        writer.endObject()
    }
}
