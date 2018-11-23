package com.vitoksmile.sample.coroutines.repository.data.api

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.data.repository.DataSource
import com.vitoksmile.sample.coroutines.repository.di.injectAPI
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext

class PostApiData : DataSource<Post> {

    private val api by injectAPI<PostApi>()

    override suspend fun getAll(): Deferred<List<Post>> = withContext(BG) {
        api.getAll()
    }
}