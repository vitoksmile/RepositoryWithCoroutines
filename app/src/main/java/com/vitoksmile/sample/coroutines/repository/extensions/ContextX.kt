package com.vitoksmile.sample.coroutines.repository.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Launch new activity
 */
inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}