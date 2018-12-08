package com.vitoksmile.sample.coroutines.repository.extensions

import com.vitoksmile.sample.coroutines.repository.data.exceptions.NoInternetException
import com.vitoksmile.sample.coroutines.repository.utils.isNetworkAvailable
import kotlinx.coroutines.CompletableDeferred

/**
 * Complete Deferred with Exception when network isn't awailable
 */
fun <T> CompletableDeferred<T>.requireNetwork() {
    if (!isNetworkAvailable()) {
        completeExceptionally(NoInternetException())
    }
}