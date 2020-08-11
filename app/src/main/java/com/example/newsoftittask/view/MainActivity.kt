package com.example.newsoftittask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsoftittask.R
import com.example.newsoftittask.adapter.PostAdapter
import com.example.newsoftittask.api.PostsApi
import com.example.newsoftittask.api.RetrofitClient
import com.example.newsoftittask.data.PostRepository
import com.example.newsoftittask.data.PostViewModel
import com.example.newsoftittask.data.PostViewModelFactory
import com.example.newsoftittask.model.Post
import com.example.newsoftittask.utils.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModelFactory: PostViewModelFactory
     lateinit var viewModel: PostViewModel
    private lateinit var postRepository: PostRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.getClient()
        postRepository = PostRepository(retrofit.create(PostsApi::class.java))
        viewModelFactory = PostViewModelFactory(application,postRepository)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PostViewModel::class.java)
        postAdapter = PostAdapter()

        recyclerview.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        viewModel.posts.observe(this, Observer {
            postAdapter.submitList(it)
        })

        viewModel.getState().observe(this, Observer {
           it?.let {
               if (it == State.Loading) loading_circular.visibility = View.VISIBLE
               else loading_circular.visibility = View.GONE
           }
        })
        //event when clicked
        postAdapter.setOnClickListener(object : PostAdapter.OnEventClickListener {
            override fun onItemClick(post: Post) {
                Toast.makeText(
                    this@MainActivity,
                    "Post id = ${post.id} \nUser id = ${post.userId}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onLongClick(post: Post) {
                val fm = supportFragmentManager
                val fragment = PopupFragment.newInstance(post)
                fragment.show(fm, "")
            }
        })
    }

}