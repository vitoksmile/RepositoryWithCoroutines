package com.vitoksmile.sample.coroutines.repository.data.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.BG
import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.UI
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext

/**
 * Hold response that can be observed as LiveData within a given lifecycle
 */
class ResponseLiveData<T : Any> : LiveData<Response<T>>() {

    fun observe(
        owner: LifecycleOwner,
        success: Success<T>,
        error: Error? = null,
        loading: Loading? = null
    ) {
        val lifecycleOwner = (owner as? Fragment)?.viewLifecycleOwner ?: owner
        super.observe(lifecycleOwner, ResponseObserver(success, error, loading))
    }

    /**
     * Receive values from suspend function
     */
    suspend fun from(action: suspend () -> Deferred<T>) = withContext(UI) {
        loading()

        try {
            val result = withContext(BG) {
                action().await()
            }
            success(result)
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Exception) {
            error(error)
        }
    }

    /**
     * Receive values from Channel
     */
    suspend fun fromChannel(action: suspend () -> ReceiveChannel<T>) = withContext(UI) {
        loading()

        try {
            // Receive items in BG
            withContext(BG) {
                val channel = action()

                for (element in channel) {
                    success(element)

                    // Show loading if channel is not closed
                    if (!channel.isClosedForReceive) {
                        loading()
                    }
                }
            }
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Exception) {
            error(error)
        }
    }

    private suspend fun loading() = withContext(UI) {
        value = Response.Loading
    }

    private suspend fun success(result: T) = withContext(UI) {
        value = Response.Success(result)
    }

    private suspend fun error(error: Exception) = withContext(UI) {
        value = Response.Error(error)
    }
}

class ResponseObserver<T : Any>(
    private val success: Success<T>,
    private val error: Error? = null,
    private val loading: Loading? = null
) : Observer<Response<T>> {

    override fun onChanged(response: Response<T>?) {
        response ?: return

        when (response) {
            is Response.Loading -> {
                loading?.invoke(true)
            }

            is Response.Success -> {
                loading?.invoke(false)
                success(response.data)
            }

            is Response.Error -> {
                loading?.invoke(false)
                error?.invoke(response.exception)
            }
        }
    }
}

sealed class Response<out T : Any> {
    object Loading : Response<Nothing>()

    class Success<out T : Any>(val data: T) : Response<T>()

    class Error(val exception: Exception) : Response<Nothing>()
}