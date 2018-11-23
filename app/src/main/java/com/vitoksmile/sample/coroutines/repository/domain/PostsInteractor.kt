package com.vitoksmile.sample.coroutines.repository.domain

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.di.repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext

class PostsInteractor {

    private val repository by repo<Post>()

    suspend fun get(scope: CoroutineScope): ReceiveChannel<List<Post>> = withContext(BG) {
        repository.getAll(scope)
    }

    suspend fun create(post: Post): Deferred<Post> = withContext(BG) {
        repository.save(post)
    }

    suspend fun saveAll(posts: List<Post>): Deferred<List<Post>> = withContext(BG) {
        repository.saveAll(posts)
    }

    suspend fun remove(post: Post): Deferred<Unit> = withContext(BG) {
        repository.remove(post)
    }

    suspend fun removeAll(posts: List<Post>): Deferred<Unit> = withContext(BG) {
        repository.removeAll(posts)
    }

    suspend fun clear(): Deferred<Unit> = withContext(BG) {
        repository.removeAll()
    }
}