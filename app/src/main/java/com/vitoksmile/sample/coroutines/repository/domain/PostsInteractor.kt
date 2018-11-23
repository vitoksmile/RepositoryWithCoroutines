package com.vitoksmile.sample.coroutines.repository.domain

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.di.repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext

class PostsInteractor {

    private val repository by repo<Post>()

    suspend fun getAll(scope: CoroutineScope): ReceiveChannel<List<Post>> = withContext(BG) {
        repository.getAll(scope)
    }
}