package com.thiosin.novus.data.network.json

import com.squareup.moshi.*
import com.thiosin.novus.data.network.model.comment.CResponseDataChild
import com.thiosin.novus.data.network.model.common.Kind

class CResponseDataChildAdapter(
    private val commentDataAdapter: CommentDataAdapter,
) : JsonAdapter<CResponseDataChild?>() {

    @FromJson
    override fun fromJson(reader: JsonReader): CResponseDataChild? {
        if (reader.peek() == JsonReader.Token.STRING) {
            reader.skipValue()
            return null
        }
        val jsonValue = reader.readJsonValue()
        val data = (jsonValue as? Map<*, *>)?.get("data")
        return CResponseDataChild(
            kind = Kind.Comment,
            data = commentDataAdapter.fromJsonValue(data)!!
        )
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: CResponseDataChild?) {
        throw NotImplementedError("Not implementing")
    }
}