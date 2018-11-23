package com.vitoksmile.sample.coroutines.repository

import android.content.Context

class Application : android.app.Application() {

    companion object {

        private lateinit var instance: Application

        fun appContext(): Context = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}