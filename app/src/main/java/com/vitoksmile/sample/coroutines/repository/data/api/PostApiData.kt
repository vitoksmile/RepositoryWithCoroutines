package com.vitoksmile.sample.coroutines.repository.data.api

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.data.repository.DataSource
import com.vitoksmile.sample.coroutines.repository.di.injectAPI
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostApiData : DataSource<Post> {

    private val apiPosts by injectAPI<PostApi>()
    private val apiComments by injectAPI<CommentApi>()

    override suspend fun getAll(): Deferred<List<Post>> = withContext(BG) {
        val posts = apiPosts.getAll().await()

        for (post in posts) {
            // Get comments to posts parallel
            launch { getCommentsByPost(post) }
        }

        CompletableDeferred(posts)
    }

    override suspend fun saveAll(items: List<Post>): Deferred<List<Post>> = withContext(BG) {
        val posts = mutableListOf<Post>()

        for (item in items) {
            // Save posts parallel
            launch {
                val post = apiPosts.create(item).await()
                posts.add(post)
            }
        }

        CompletableDeferred(posts)
    }

    override suspend fun removeAll(items: List<Post>) = withContext(BG) {
        for (item in items) {
            // Remove posts parallel
            launch { apiPosts.remove(item.id).await() }
        }
    }

    override suspend fun removeAll() {}

    private suspend fun getCommentsByPost(post: Post) {
        val comments = apiComments.getByPostId(post.id).await()
        post.comments.addAll(comments)
    }
}