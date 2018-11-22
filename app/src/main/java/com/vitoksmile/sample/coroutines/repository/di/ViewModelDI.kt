package com.vitoksmile.sample.coroutines.repository.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Returns an existing ViewModel or creates a new one in the scope (usually, a fragment or
 * an activity), associated with this ViewModelProvider.
 */
inline fun <reified T : ViewModel> FragmentActivity.injectViewModel() = lazy {
    ViewModelProviders.of(this).get(T::class.java)
}