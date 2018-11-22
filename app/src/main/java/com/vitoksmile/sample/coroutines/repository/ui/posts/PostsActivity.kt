package com.vitoksmile.sample.coroutines.repository.ui.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.di.injectViewModel
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by injectViewModel()

    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        recyclerView.adapter = adapter

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.init()

        viewModel.posts.observe(this, Observer {
            val posts = it ?: return@Observer

            adapter.setPosts(posts)
        })
    }
}