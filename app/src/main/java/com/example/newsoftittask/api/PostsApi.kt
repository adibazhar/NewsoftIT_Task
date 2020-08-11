package com.example.newsoftittask.api

import com.example.newsoftittask.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostsApi {

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId:Int): Response<List<Post>>

    @PATCH("posts/{id}")
    suspend fun patchPost(@Path("id") id: Int, @Body post: Post):Response<Post>
}