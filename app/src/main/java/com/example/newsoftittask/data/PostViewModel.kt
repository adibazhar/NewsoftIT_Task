package com.example.newsoftittask.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsoftittask.model.Post
import com.example.newsoftittask.paging.PostDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PostViewModel(application: Application,private val repository: PostRepository) :
    AndroidViewModel(application) {

    var posts: LiveData<PagedList<Post>> = getListPosts()
    var updateResult = MutableLiveData<Post>()
    val postDataSource = PostDataSource(viewModelScope)


    fun getListPosts(): LiveData<PagedList<Post>> {
        val config = PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(true).build()
        val dataSourceFactory = object : DataSource.Factory<Int, Post>() {
            override fun create(): DataSource<Int, Post> {
                return postDataSource
            }
        }

        return (LivePagedListBuilder<Int, Post>(dataSourceFactory, config).build())
    }

    fun getState() = postDataSource.state

    fun updatePosts(id: Int, post: Post) {
        viewModelScope.launch {
            val response = repository.updatePost(id, post)
            if (!response.isSuccessful) {
                Log.d("PostViewmodel", "${response.body()}")
                return@launch
            }
            updateResult.value = response.body()
        }


    }
}