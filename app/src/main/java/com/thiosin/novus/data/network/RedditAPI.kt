package com.thiosin.novus.data.network

import okhttp3.ResponseBody
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
        @Query("after") after: String? = ""
    ): ResponseBody
}
