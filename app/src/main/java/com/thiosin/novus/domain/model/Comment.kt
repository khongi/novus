package com.thiosin.novus.domain.model

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.comment.CommentData
import com.thiosin.novus.data.network.model.comment.Distinguished
import timber.log.Timber

data class Comment(
    val body: String,
    val author: String,
    val isOP: Boolean,
    val votes: Int,
    val depth: Int,
    val relativeTime: String,
    val authorType: AuthorType,
    val replies: List<Comment>,
)

enum class AuthorType {
    NORMAL,
    MODERATOR,
    ADMIN
}

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
        relativeTime = getRelativeTime(createdUTC.toLong()),
        authorType = getDistinguished(distinguished),
        replies = repliedComments
    )
}

fun getDistinguished(distinguished: Distinguished?): AuthorType {
    return when (distinguished) {
        Distinguished.MODERATOR -> AuthorType.MODERATOR
        Distinguished.ADMIN -> AuthorType.ADMIN
        null -> AuthorType.NORMAL
    }
}

