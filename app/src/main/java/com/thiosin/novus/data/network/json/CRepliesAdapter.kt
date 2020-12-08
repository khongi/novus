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
        // This should be a CResponse
        reader.beginObject()
        val replies = mutableListOf<CResponseDataChild>()

        // Unwrap and skip the unnecessary data structure
        while (reader.hasNext()) {
            when (reader.selectName(JsonReader.Options.of("data"))) {
                0 -> {
                    reader.beginObject()

                    while (reader.hasNext()) {
                        when (reader.selectName(JsonReader.Options.of("children"))) {
                            0 -> {
                                reader.beginArray()

                                while (reader.hasNext()) {
                                    val cResponseDataChild = adapter.fromJson(reader)
                                    if (cResponseDataChild == null) {
                                        Timber.e("CResponseDataChild was NULL")
                                        throw Util.unexpectedNull(
                                            "nested data",
                                            "nested data",
                                            reader)
                                    }
                                    replies.add(cResponseDataChild)
                                }

                                reader.endArray()
                            }
                            -1 -> {
                                reader.skipName()
                                reader.skipValue()
                            }
                        }
                    }

                    reader.endObject()
                }
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }

        reader.endObject()
        return CReplies(data = replies)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: CReplies?) {
        throw NotImplementedError("Not implementing")
    }
}