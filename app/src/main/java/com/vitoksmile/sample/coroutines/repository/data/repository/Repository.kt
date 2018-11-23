package com.vitoksmile.sample.coroutines.repository.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay

class Repository<E>(
    private val api: DataSource<E>,
    private val db: DataSource<E>
) {

    fun getAll(scope: CoroutineScope): ReceiveChannel<List<E>> = scope.produce {
        // Get items from DB first
        delay(1000)
        val itemsDB = db.getAll().await()
        send(itemsDB)

        // Get items from API
        delay(3000)
        val itemsAPI = api.getAll().await()
        send(itemsAPI)

        close()
    }
}