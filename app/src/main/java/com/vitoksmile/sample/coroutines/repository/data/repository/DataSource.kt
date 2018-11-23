package com.vitoksmile.sample.coroutines.repository.data.repository

import kotlinx.coroutines.Deferred

interface DataSource<E> {

    suspend fun getAll(): Deferred<List<E>>
}