package com.vitoksmile.sample.coroutines.repository.ui.posts

import com.vitoksmile.sample.coroutines.repository.coroutines.ScopedViewModel
import com.vitoksmile.sample.coroutines.repository.data.models.Post
import com.vitoksmile.sample.coroutines.repository.data.viewmodel.ResponseLiveData
import com.vitoksmile.sample.coroutines.repository.domain.PostsInteractor
import kotlinx.coroutines.launch

class PostsViewModel : ScopedViewModel() {

    private val interactor = PostsInteractor()

    /**
     * Hold posts
     */
    val postsData = ResponseLiveData<List<Post>>()

    /**
     * Hold action when post was removed
     *
     * TODO: should be replaced to ActionLiveData, the LiveData will clear value after consume
     */
    val removePostData = ResponseLiveData<Unit>()

    fun init() {
        if (postsData.value != null) {
            return
        }

        getPosts()
    }

    fun removePost(post: Post) = launch {
        removePostData.from {
            interactor.remove(post)
        }
    }

    private fun getPosts() = launch {
        postsData.fromChannel {
            interactor.get(this)
        }
    }
}