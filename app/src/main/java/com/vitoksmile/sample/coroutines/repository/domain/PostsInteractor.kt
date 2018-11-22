package com.vitoksmile.sample.coroutines.repository.domain

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.api.PostApi
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.di.injectAPI
import kotlinx.coroutines.withContext

class PostsInteractor {

    private val api by injectAPI<PostApi>()

    suspend fun getAll(): List<Post> = withContext(BG) {
        api.getAll().await()
    }
}