package com.vitoksmile.sample.coroutines.repository.data.api

import com.vitoksmile.sample.coroutines.repository.data.models.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    fun getAll(): Deferred<List<Post>>
}