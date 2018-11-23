package com.vitoksmile.sample.coroutines.repository.ui.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.di.injectViewModel
import com.vitoksmile.sample.coroutines.repository.ui.posts.comments.CommentsBottomSheetDialog
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by injectViewModel()

    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.onPostClickListener = this::onPostClicked

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.init()

        viewModel.posts.observe(this,
                success = { posts ->
                    adapter.setPosts(posts)
                },
                error = { error ->
                    error.printStackTrace()
                },
                loading = { isLoading ->
                    loadingView.setLoading(isLoading)
                }
        )
    }

    private fun onPostClicked(post: Post) {
        if (post.comments.isEmpty()) {
            return
        }

        CommentsBottomSheetDialog.show(supportFragmentManager, post.comments)
    }
}