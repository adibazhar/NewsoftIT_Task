package com.example.newsoftittask.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.newsoftittask.api.PostsApi
import com.example.newsoftittask.api.RetrofitClient
import com.example.newsoftittask.model.Post
import com.example.newsoftittask.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostDataSource(val scope: CoroutineScope) : ItemKeyedDataSource<Int, Post>() {
    val api = RetrofitClient.getClient().create(PostsApi::class.java)
    var state = MutableLiveData<State>()


        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Post>) {
        scope.launch {
            val response = api.getPosts(1 )
            if (response.isSuccessful) {
                val data = response.body()
                callback.onResult(
                    data!!.toMutableList()
                )
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Post>) {
        scope.launch {
            state.postValue(State.Loading)

            //delay to illustrate loading while fetching data using paging
            delay(1000)
            val response = api.getPosts(params.key+1)
            if (response.isSuccessful){
                val data = response.body()
                data?.let {
                    callback.onResult(it.toMutableList())
                }
                state.postValue(State.Done)
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Post>) {

    }

    override fun getKey(item: Post): Int {
        return item.userId
    }

}