package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditAPI {

    @GET("/r/{subreddit}/{sort}.json")
    suspend fun getSubmissions(
        @Path("subreddit") subreddit: String,
        @Path("sort") sort: String = "hot",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = "",
    ): SubmissionListingResponse

    @GET("{sort}.json")
    suspend fun getFrontpage(
        @Path("sort") sort: String = "hot",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = "",
    ): SubmissionListingResponse

    @GET("/subreddits/{where}.json")
    suspend fun getSubreddits(
        @Path("where") where: String = "default",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = "",
    ): SubredditListingResponse

    @GET("/comments/{article}.json")
    suspend fun getComments(
        @Path("article") article: String = "k894rv",
        @Query("sort") sort: String = "best",
        @Query("context") context: Int = 0,
        @Query("showedits") showEdits: Boolean = false,
        @Query("showmore") showMore: Boolean = false,
        @Query("depth") depth: Int = 5,
    ): List<CResponse>
}
