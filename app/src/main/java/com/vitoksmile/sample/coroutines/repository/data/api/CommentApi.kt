package com.vitoksmile.sample.coroutines.repository.data.api

import com.vitoksmile.sample.coroutines.repository.data.models.Comment
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApi {

    @GET("posts/{postId}/comments")
    fun getByPostId(@Path("postId") postId: Int): Deferred<List<Comment>>
}