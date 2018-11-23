package com.vitoksmile.sample.coroutines.repository.data.viewmodel

typealias Loading = (isLoading: Boolean) -> Unit

typealias Success<T> = (data: T) -> Unit

typealias Error = (error: Exception) -> Unit