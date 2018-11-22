package com.vitoksmile.sample.coroutines.repository.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private val posts = mutableListOf<Post>()

    fun setPosts(posts: List<Post>) {
        this.posts.apply {
            clear()
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvId = view.tvId
        private val tvTitle = view.tvTitle
        private val tvBody = view.tvBody

        fun bind(post: Post) {
            with(post) {
                tvId.text = "ID #$id"
                tvTitle.text = title
                tvBody.text = body
            }
        }
    }
}