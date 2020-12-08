package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.comment.CResponseDataChild
import timber.log.Timber

data class Comment(
    val body: String,
    val replies: List<Comment>,
)

fun List<CResponse>.toCommentList(): List<Comment> {
    val resultList = mutableListOf<Comment>()

    forEach { responseWrapper ->
        responseWrapper.data.children.forEach { dataChild ->
            val comment = dataChild.toComment()
            resultList.add(comment)
        }
    }

    return resultList
}

fun CResponseDataChild.toComment(): Comment {
    fun getDepthString(depth: Long): String {
        return "--  ".repeat(1 + depth.toInt())
    }

    Timber.v("${getDepthString(this.data.depth)} ${this.data.score} ${
        this.data.body.trim().substringBefore('\n').take(40)
    }")
    val replies = mutableListOf<Comment>()
    this.data.replies.data?.forEach {
        val reply = it.toComment()
        replies.add(reply)
    }
    return Comment(this.data.body, replies)
}
