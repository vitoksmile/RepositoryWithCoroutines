package com.vitoksmile.sample.coroutines.repository.data.repository

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.extensions.requireNetwork
import com.vitoksmile.sample.coroutines.repository.utils.isNetworkAvailable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.withContext

@Suppress("EXPERIMENTAL_API_USAGE")
class Repository<E>(
    private val api: DataSource<E>,
    private val db: DataSource<E>
) {

    suspend fun getAll(scope: CoroutineScope): ReceiveChannel<List<E>> = scope.produce {
        // Send items from DB first
        send(db.getAll().await())

        if (isNetworkAvailable()) {
            // Get items from API
            val items = api.getAll().await()

            // Remove all items from DB
            db.removeAll()

            // Save new items from API to DB
            db.saveAll(items)

            // Send items from API
            send(items)
        }

        close()
    }

    suspend fun save(item: E): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            try {
                saveAll(listOf(item)).await()
                complete(Unit)
            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun saveAll(items: List<E>): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            requireNetwork()

            try {
                // Save items to API first
                api.saveAll(items)

                // Then save items to DB
                db.saveAll(items)

                // Complete deferred
                complete(Unit)

            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun remove(item: E): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            try {
                removeAll(listOf(item)).await()
                complete(Unit)
            } catch (error: Exception) {
                completeExceptionally(error)
            }
        }
    }

    suspend fun removeAll(items: List<E>): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            requireNetwork()

            // Remove items from API first
            try {
                api.removeAll(items)
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            // Then remove items from DB
            try {
                db.removeAll(items)
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            complete(Unit)
        }
    }

    suspend fun removeAll(): Deferred<Unit> = withContext(BG) {
        CompletableDeferred<Unit>().apply {
            // Remove items from API first
            try {
                api.removeAll()
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            // Then remove items from DB
            try {
                db.removeAll()
            } catch (error: Exception) {
                completeExceptionally(error)
            }

            complete(Unit)
        }
    }
}