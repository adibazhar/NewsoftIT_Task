package com.example.newsoftittask.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostViewModelFactory(
    private val application: Application,
    private val postRepository: PostRepository
) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(application,postRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}