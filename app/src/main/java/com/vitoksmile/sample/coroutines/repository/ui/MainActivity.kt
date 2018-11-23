package com.vitoksmile.sample.coroutines.repository.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vitoksmile.sample.coroutines.repository.R
import com.vitoksmile.sample.coroutines.repository.extensions.startActivity
import com.vitoksmile.sample.coroutines.repository.ui.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPosts.setOnClickListener {
            startActivity<PostsActivity>()
        }
    }
}
