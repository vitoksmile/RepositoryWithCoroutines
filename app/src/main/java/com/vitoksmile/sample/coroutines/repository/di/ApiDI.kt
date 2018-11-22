package com.vitoksmile.sample.coroutines.repository.di

import com.vitoksmile.sample.coroutines.repository.data.api.ApiServiceBuilder

/**
 * Create an implementation of the API endpoints
 */
inline fun <reified T> injectAPI() = lazy {
    ApiServiceBuilder.create(T::class.java)
}