package com.thiosin.novus.data.network.json

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import com.thiosin.novus.data.network.model.comment.CReplies
import com.thiosin.novus.data.network.model.comment.CResponseDataChild
import timber.log.Timber

class CRepliesAdapter(
    private val getCResponseDataChildAdapter: () -> CResponseDataChildAdapter,
) : JsonAdapter<CReplies>() {

    @FromJson
    override fun fromJson(reader: JsonReader): CReplies? {
        val nextToken = reader.peek()
        val adapter = getCResponseDataChildAdapter()
        if (nextToken == JsonReader.Token.STRING) {
            reader.skipValue()
            return CReplies(data = null)
        }
        // Next node should be a CResponse
        val replies = parseReplies(reader, adapter)
        return CReplies(data = replies)
    }

    private fun parseReplies(
        reader: JsonReader,
        adapter: CResponseDataChildAdapter,
    ): List<CResponseDataChild> {
        reader.beginObject()
        val replies = mutableListOf<CResponseDataChild>()

        // Unwrap and skip the unnecessary data structure
        while (reader.hasNext()) {
            when (reader.selectName(JsonReader.Options.of("data"))) {
                0 -> {
                    parseRepliesFromData(reader, adapter, replies)
                }
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }

        reader.endObject()
        return replies
    }

    private fun parseRepliesFromData(
        reader: JsonReader,
        adapter: CResponseDataChildAdapter,
        replies: MutableList<CResponseDataChild>,
    ) {
        reader.beginObject()

        while (reader.hasNext()) {
            when (reader.selectName(JsonReader.Options.of("children"))) {
                0 -> {
                    parseRepliesFromChildren(reader, adapter, replies)
                }
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }

        reader.endObject()
    }

    private fun parseRepliesFromChildren(
        reader: JsonReader,
        adapter: CResponseDataChildAdapter,
        replies: MutableList<CResponseDataChild>,
    ) {
        reader.beginArray()

        while (reader.hasNext()) {
            parseReply(adapter, reader, replies)
        }

        reader.endArray()
    }

    private fun parseReply(
        adapter: CResponseDataChildAdapter,
        reader: JsonReader,
        replies: MutableList<CResponseDataChild>,
    ) {
        val cResponseDataChild = adapter.fromJson(reader)
        if (cResponseDataChild == null) {
            Timber.e("CResponseDataChild was NULL")
            throw Util.unexpectedNull(
                "nested data",
                "nested data",
                reader
            )
        }
        replies.add(cResponseDataChild)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: CReplies?) {
        throw NotImplementedError("Not implementing")
    }
}
