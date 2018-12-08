package com.vitoksmile.sample.coroutines.repository.data.db

import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.data.repository.DataSource
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Imitation of DB source
 */
class PostDbData : DataSource<Post> {

    override suspend fun getAll(): Deferred<List<Post>> = withContext(BG) {
        val result = mutableListOf<Post>()

        for (i in 0 until 10) {
            result.add(fakePost(i + 1))
        }

        CompletableDeferred(result)
    }

    override suspend fun saveAll(items: List<Post>) {}

    override suspend fun removeAll(items: List<Post>) {}

    override suspend fun removeAll() {}

    private fun fakePost(id: Int) = Post(
        id = id,
        userId = Math.abs(Random.nextInt()),
        title = "DB: ${randomString(10)}",
        body = randomString(30)
    )

    private fun randomString(length: Int): String {
        val leftLimit = 97 // letter 'a'
        val rightLimit = 122 // letter 'z'
        val buffer = StringBuilder(length)
        for (i in 0 until length) {
            val randomLimitedInt = leftLimit + (Random.nextFloat() * (rightLimit - leftLimit + 1)).toInt()
            buffer.append(randomLimitedInt.toChar())
        }
        return buffer.toString()
    }
}