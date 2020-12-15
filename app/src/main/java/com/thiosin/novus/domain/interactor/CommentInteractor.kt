package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.Comment
import com.thiosin.novus.domain.model.toCommentList
import javax.inject.Inject

class CommentInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun getComments(submissionId: String): List<Comment> {
        return networkDataSource.getComments(submissionId).toCommentList()
    }
}
