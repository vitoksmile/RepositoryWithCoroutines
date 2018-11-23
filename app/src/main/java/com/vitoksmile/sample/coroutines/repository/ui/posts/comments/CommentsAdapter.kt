package com.vitoksmile.sample.coroutines.repository.ui.posts.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.data.models.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentsAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount() = comments.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.tvName
        private val tvEmail = view.tvEmail
        private val tvBody = view.tvBody

        fun bind(comment: Comment) {
            with(comment) {
                tvName.text = name
                tvEmail.text = email
                tvBody.text = body
            }
        }
    }
}