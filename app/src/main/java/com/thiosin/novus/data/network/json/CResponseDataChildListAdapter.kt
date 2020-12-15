package com.thiosin.novus.data.network.json

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import com.thiosin.novus.data.network.model.comment.CResponseDataChild
import timber.log.Timber

class CResponseDataChildListAdapter(
    moshi: Moshi,
) : JsonAdapter<List<CResponseDataChild>>() {
    private var cResponseDataChildAdapter: CResponseDataChildAdapter? = null
    private val repliesAdapter = CRepliesAdapter {
        val initializedCResponseDataChildAdapter = cResponseDataChildAdapter
            ?: throw IllegalStateException("CResponseDataChildAdapter is not initialized")
        initializedCResponseDataChildAdapter
    }
    private val commentDataAdapter = CommentDataAdapter(moshi, repliesAdapter)

    init {
        cResponseDataChildAdapter = CResponseDataChildAdapter(commentDataAdapter)
    }

    @FromJson
    override fun fromJson(reader: JsonReader): List<CResponseDataChild> {
        val resultList = mutableListOf<CResponseDataChild>()
        reader.beginArray()

        while (reader.hasNext()) {
            val jsonValue = reader.readJsonValue()
            val kind = (jsonValue as? Map<*, *>)?.get("kind")?.toString()
            if (kind == "t1") {
                val responseDataChild = cResponseDataChildAdapter?.fromJsonValue(jsonValue)
                if (responseDataChild != null) {
                    resultList.add(responseDataChild)
                } else {
                    Timber.e("CResponseDataChild was NULL")
                    throw Util.unexpectedNull("data", "data", reader)
                }
            }
        }

        reader.endArray()
        return resultList
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: List<CResponseDataChild>?) {
        throw NotImplementedError("Not implementing")
    }
}
