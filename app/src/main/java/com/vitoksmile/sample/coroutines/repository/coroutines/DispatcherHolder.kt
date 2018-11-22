package com.vitoksmile.sample.coroutines.repository.coroutines

import kotlinx.coroutines.Dispatchers

object DispatcherHolder {

    val UI = Dispatchers.Main

    val BG = Dispatchers.IO
}