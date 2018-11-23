package com.vitoksmile.sample.coroutines.repository.extensions

import androidx.fragment.app.Fragment

/**
 * Returns the entry with the given key as an object from arguments
 */
fun <T : Any> Fragment.args(key: String) = lazy {
    arguments?.get(key) as T
}