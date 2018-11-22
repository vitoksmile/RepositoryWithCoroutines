package com.vitoksmile.sample.coroutines.repository.data.models

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)