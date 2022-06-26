package br.com.coupledev.launchnews.presentation.ui.home

import androidx.lifecycle.*
import br.com.coupledev.launchnews.core.RemoteException
import br.com.coupledev.launchnews.core.State
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>> get() = _listPost

    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible: LiveData<Boolean> get() = _progressBarVisible

    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?> get() = _snackbar

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(State.Loading)
                    delay(800)
                }
                .catch {
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackbar.value = exception.message
                }
                .collect { listPost ->
                    _listPost.postValue(State.Success(listPost))
                }
        }
    }

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    val errorText = Transformations.map(listPost) { state ->
        when(state) {
            State.Loading -> { "Loading latest news..." }
            is State.Error -> { "Houston we've had a problem!" }
            else -> { "" }
        }
    }

}