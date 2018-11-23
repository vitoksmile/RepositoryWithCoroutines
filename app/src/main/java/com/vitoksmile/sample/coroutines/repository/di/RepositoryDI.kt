package com.vitoksmile.sample.coroutines.repository.di

import com.vitoksmile.sample.coroutines.repository.data.repository.RepositoryFactory

/**
 * Create instance of repository
 */
inline fun <reified T : Any> repo() = lazy {
    RepositoryFactory.create<T>()
}