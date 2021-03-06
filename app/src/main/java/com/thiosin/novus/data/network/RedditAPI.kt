package com.thiosin.novus.data.network

import com.thiosin.novus.data.network.model.comment.CResponse
import com.thiosin.novus.data.network.model.submission.SubmissionListingResponse
import com.thiosin.novus.data.network.model.subreddit.SubredditListingResponse
import com.thiosin.novus.data.network.model.user.MeResponse
import retrofit2.http.*

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
        @Query("limit") limit: Int = 100,
        @Query("count") count: Int = 0,
        @Query("after") after: String? = "",
    ): SubredditListingResponse

    @GET("/subreddits/mine/{where}.json")
    suspend fun getMySubreddits(
        @Path("where") where: String = "subscriber",
        @Query("limit") limit: Int = 100,
        @Query("count") count: Int = 0,
        @Query("after") after: String? = "",
    ): SubredditListingResponse

    @GET("/comments/{article}.json")
    suspend fun getComments(
        @Path("article") article: String,
        @Query("sort") sort: String = "best",
        @Query("context") context: Int = 0,
        @Query("showedits") showEdits: Boolean = false,
        @Query("showmore") showMore: Boolean = false,
        @Query("depth") depth: Int = 10,
    ): List<CResponse>

    @GET("/api/me.json")
    suspend fun getMe(): MeResponse

    @FormUrlEncoded
    @POST("/api/vote")
    suspend fun vote(
        @Field("dir") dir: Int,
        @Field("id") id: String,
    )
}
