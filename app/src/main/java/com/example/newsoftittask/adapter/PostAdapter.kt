package com.example.newsoftittask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsoftittask.R
import com.example.newsoftittask.model.Post
import com.example.newsoftittask.utils.State
import kotlinx.android.synthetic.main.list_item_rv.view.*

class PostAdapter:PagedListAdapter<Post, PostAdapter.ItemViewHolder>(
    DiffCallback
) {

    private var listener : OnEventClickListener? = null
    private var state:State? = null
    companion object {
        private val DiffCallback = object :DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_rv,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentPost = getItem(position)!!

        state?.let {
            holder.bind(it)
        }

        holder.tv_title.text = currentPost.title
        holder.tv_body.text = currentPost.message
    }

    fun setLoadingState(state: State){
        this.state = state
    }

    inner class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                listener?.let { getItem(adapterPosition)?.let { post -> it.onItemClick(post) } }
            }
            itemView.setOnLongClickListener {
                getItem(adapterPosition)?.let { post -> listener?.onLongClick(post) }
                return@setOnLongClickListener true
            }
        }

        fun bind(state: State){

        }

        val tv_title = itemView.tv_title
        val tv_body = itemView.tv_body
    }

    interface OnEventClickListener {
        fun onItemClick(post: Post)

        fun onLongClick(post: Post)
    }

    fun setOnClickListener(listener:OnEventClickListener){
        this.listener = listener
    }

}