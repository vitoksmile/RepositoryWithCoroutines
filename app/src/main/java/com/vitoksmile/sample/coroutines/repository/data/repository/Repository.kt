package com.vitoksmile.sample.coroutines.repository.data.repository

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.exceptions.NoInternetException
import com.vitoksmile.sample.coroutines.repository.utils.isNetworkAvailable
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class Repository<E>(
    private val api: DataSource<E>,
    private val db: DataSource<E>
) {

    suspend fun getAll(scope: CoroutineScope): ReceiveChannel<List<E>> = scope.produce {
        try {
            // Get items from DB first
            val itemsDB = db.getAll().await()
            send(itemsDB)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

        if (isNetworkAvailable()) {
            try {
                // Get items from API
                val itemsAPI = api.getAll().await()
                send(itemsAPI)
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }

        close()
    }

    suspend fun save(item: E): Deferred<E> = withContext(BG) {
        CompletableDeferred<E>().apply {
            try {
                val result = saveAll(listOf(item)).await().first()
                complete(result)
            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun saveAll(items: List<E>): Deferred<List<E>> = withContext(BG) {
        CompletableDeferred<List<E>>().apply {
            if (!isNetworkAvailable()) {
                completeExceptionally(NoInternetException())
            }

            try {
                // Save items to API first
                val itemsAPI = api.saveAll(items).await()

                // Then save items to DB
                db.saveAll(itemsAPI).await()

                // Return saved items
                complete(itemsAPI)

            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun remove(item: E): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            try {
                removeAll(listOf(item))
                complete(Unit)
            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun removeAll(items: List<E>): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            if (!isNetworkAvailable()) {
                completeExceptionally(NoInternetException())
            }

            // Remove items from DB first
            try {
                db.removeAll(items)
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            // Then remove items from API
            try {
                api.removeAll(items)
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            complete(Unit)
        }
    }

    suspend fun removeAll(): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            // Remove items from DB first
            try {
                db.removeAll()
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            // Then remove items from API
            try {
                api.removeAll()
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            complete(Unit)
        }
    }
}