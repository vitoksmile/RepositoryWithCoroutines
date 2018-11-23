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

    suspend fun from(job: Deferred<T>) = withContext(UI) {
        value = Response.Loading

        try {
            val result = withContext(BG) {
                job.await()
            }
            value = Response.Success(result)
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Exception) {
            value = Response.Error(error)
        }
    }

    suspend fun from(channel: ReceiveChannel<T>) = withContext(UI) {
        value = Response.Loading

        try {
            withContext(BG) {
                for (element in channel) {
                    postValue(Response.Success(element))
                }
            }
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Exception) {
            value = Response.Error(error)
        }
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