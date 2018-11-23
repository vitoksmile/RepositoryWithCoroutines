package com.vitoksmile.sample.coroutines.repository.data.models

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,

    private val comments_: MutableList<Comment>? = null
) {

    /**
     * We can't just use
     * val comments: MutableList<Comment> = mutableListOf()
     * because, GsonConverterFactory sets the value as null, because response from API doesn't contains the field
     * And after do something with the reference, example, comments.add(Comment), system throws NullPointerException
     */
    val comments: MutableList<Comment>
        get() = comments_ ?: mutableListOf()
}