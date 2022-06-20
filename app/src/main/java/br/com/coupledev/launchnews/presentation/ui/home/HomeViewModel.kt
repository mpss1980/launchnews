package br.com.coupledev.launchnews.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.data.repository.MockAPIService
import br.com.coupledev.launchnews.data.repository.PostRepositoryImpl
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val repository: PostRepository = PostRepositoryImpl(MockAPIService)

    private val _listPost = MutableLiveData<List<Post>>()
    val listPost: LiveData<List<Post>> get() = _listPost

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts().collect {
                _listPost.value = it
            }
        }
    }
}