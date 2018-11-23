package com.vitoksmile.sample.coroutines.repository.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitoksmile.sample.coroutines.repository.coroutines.ScopedViewModel
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.domain.PostsInteractor
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class PostsViewModel : ScopedViewModel() {

    private val interactor = PostsInteractor()

    private val posts_ = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = posts_

    fun init() {
        if (posts.value != null) {
            return
        }

        getPosts()
    }

    private fun getPosts() = launch {
        try {
            val result = interactor.getAll(this)
            for (element in result) {
                posts_.value = element
            }
        } catch (canceled: CancellationException) {
            // Canceled by user
        } catch (error: Exception) {
            // Something was wrong
            error.printStackTrace()
        }
    }
}