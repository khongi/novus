package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.comment.CommentData
import timber.log.Timber

data class Comment(
    val body: String,
    val author: String,
    val isOP: Boolean,
    val votes: Int,
    val depth: Int,
    val isCollapsed: Boolean,
    val relativeTime: String,
    val replies: List<Comment>,
)

fun List<CResponse>.toCommentList(): List<Comment> {
    val resultList = mutableListOf<Comment>()

    forEach { responseWrapper ->
        responseWrapper.data.children.forEach { dataChild ->
            val comment = dataChild.data.toComment()
            resultList.add(comment)
        }
    }

    return resultList
}

private fun getDepthString(depth: Long): String {
    return "--  ".repeat(1 + depth.toInt())
}

fun CommentData.toComment(): Comment {
    Timber.v("${getDepthString(depth)} $score ${
        body.trim().substringBefore('\n').take(40)
    }")

    val repliedComments = mutableListOf<Comment>()

    this.replies.data?.forEach {
        val reply = it.data.toComment()
        repliedComments.add(reply)
    }

    return Comment(
        body = body.trim(),
        author = author,
        isOP = isSubmitter,
        votes = score.toInt(),
        depth = depth.toInt(),
        isCollapsed = collapsed,
        relativeTime = getRelativeTime(created.toInt()),
        replies = repliedComments
    )
}

