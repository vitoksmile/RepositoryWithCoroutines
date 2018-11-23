package com.vitoksmile.sample.coroutines.repository.data.models

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val comments: MutableList<Comment> = mutableListOf()
) {

    constructor() : this(0, 0, "", "", mutableListOf<Comment>())
}