package com.vitoksmile.sample.coroutines.repository.ui.posts

import android.os.Bundle
import android.widget.Toast
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
        adapter.onPostLongClickListener = this::onPostLongClicked

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.init()

        viewModel.postsData.observe(this,
            { posts -> adapter.setPosts(posts) },
            { error -> showError(error) },
            { isLoading -> showLoading(isLoading) }
        )

        viewModel.removePostData.observe(this,
            { },
            { error -> showError(error) },
            { isLoading -> showLoading(isLoading) })
    }

    private fun showLoading(isLoading: Boolean) {
        loadingView.setLoading(isLoading)
    }

    private fun showError(error: Exception) {
        Toast.makeText(this, "Exception $error", Toast.LENGTH_LONG).show()
    }

    private fun onPostClicked(post: Post) {
        if (post.comments.isEmpty()) {
            return
        }

        CommentsBottomSheetDialog.show(supportFragmentManager, post.comments)
    }

    private fun onPostLongClicked(post: Post) {
        viewModel.removePost(post)
    }
}