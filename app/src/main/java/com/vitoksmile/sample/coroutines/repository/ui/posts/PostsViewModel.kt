package com.vitoksmile.sample.coroutines.repository.ui.posts

import com.vitoksmile.sample.coroutines.repository.coroutines.ScopedViewModel
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.data.viewmodel.ResponseLiveData
import com.vitoksmile.sample.coroutines.repository.domain.PostsInteractor
import kotlinx.coroutines.launch

class PostsViewModel : ScopedViewModel() {

    private val interactor = PostsInteractor()

    val posts = ResponseLiveData<List<Post>>()

    fun init() {
        if (posts.value != null) {
            return
        }

        getPosts()
    }

    private fun getPosts() = launch {
        posts.from(interactor.getAll(this))
    }
}