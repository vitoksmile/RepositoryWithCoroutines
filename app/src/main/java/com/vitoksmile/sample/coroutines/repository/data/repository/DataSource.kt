package com.vitoksmile.sample.coroutines.repository.data.repository

import kotlinx.coroutines.Deferred

interface DataSource<E> {

    suspend fun getAll(): Deferred<List<E>>

    suspend fun saveAll(items: List<E>)

    suspend fun removeAll(items: List<E>)

    suspend fun removeAll()
}