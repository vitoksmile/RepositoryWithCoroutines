package com.vitoksmile.sample.coroutines.repository.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar

class LoadingView(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    init {
        val progressBar = ProgressBar(context)
        val params = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        addView(progressBar, params)
    }

    fun setLoading(isLoading: Boolean) {
        visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}