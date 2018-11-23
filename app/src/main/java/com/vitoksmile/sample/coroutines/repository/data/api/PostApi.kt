package com.vitoksmile.sample.coroutines.repository.data.api

import com.vitoksmile.sample.coroutines.repository.data.models.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {

    @GET("posts")
    fun getAll(): Deferred<List<Post>>

    @POST("posts")
    fun create(post: Post): Deferred<Post>

    @DELETE("posts/{id}")
    fun remove(@Path("id") id: Int): Deferred<Unit>
}