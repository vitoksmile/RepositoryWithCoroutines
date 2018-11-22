package com.vitoksmile.sample.coroutines.repository.coroutines

import androidx.lifecycle.ViewModel
import com.vitoksmile.sample.coroutines.repository.coroutines.DispatcherHolder.UI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class ScopedViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = UI + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}