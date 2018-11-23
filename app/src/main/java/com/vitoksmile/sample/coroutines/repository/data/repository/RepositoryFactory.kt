package com.vitoksmile.sample.coroutines.repository.data.repository

import com.vitoksmile.sample.coroutines.repository.data.api.PostApiData
import com.vitoksmile.sample.coroutines.repository.data.db.PostDbData
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import kotlin.reflect.KClass

object RepositoryFactory {

    /**
     * Create instance of repository
     *
     * @return new repository instance
     */
    inline fun <reified T : Any> create(): Repository<T> {
        return Repository(
            createAPISource(T::class),
            createDBSource(T::class)
        )
    }

    /**
     * Create instance of API source
     *
     * @return new source instance
     */
    fun <T : Any> createAPISource(kClass: KClass<T>): DataSource<T> {
        return when (kClass) {
            Post::class -> PostApiData()

            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<T>
    }

    /**
     * Create instance of DB source
     *
     * @return new source instance
     */
    fun <T : Any> createDBSource(kClass: KClass<T>): DataSource<T> {
        return when (kClass) {
            Post::class -> PostDbData()

            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<T>
    }
}