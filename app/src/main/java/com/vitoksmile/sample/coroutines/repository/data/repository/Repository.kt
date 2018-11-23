package com.vitoksmile.sample.coroutines.repository.data.repository

import com.vitoksmile.sample.coroutines.repository.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class Repository<E>(
    private val api: DataSource<E>,
    private val db: DataSource<E>
) {

    fun getAll(scope: CoroutineScope): ReceiveChannel<List<E>> = scope.produce {
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
}