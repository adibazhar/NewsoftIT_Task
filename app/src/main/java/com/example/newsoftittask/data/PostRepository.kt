package com.example.newsoftittask.data

import android.util.Log
import com.example.newsoftittask.api.PostsApi
import com.example.newsoftittask.model.Post
import retrofit2.Response
import retrofit2.Retrofit

class PostRepository(private val api: PostsApi) {

//    suspend fun fetchPosts():Response<List<Post>>{
//        return api.getPosts()
//    }

    suspend fun updatePost(id:Int,post: Post):Response<Post>{
        return api.patchPost(id,post)
    }
}