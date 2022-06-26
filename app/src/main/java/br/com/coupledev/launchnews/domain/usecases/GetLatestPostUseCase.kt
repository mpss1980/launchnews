package br.com.coupledev.launchnews.domain.usecases

import br.com.coupledev.launchnews.core.Usecase
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostUseCase(private val repository: PostRepository) : Usecase<String, List<Post>>() {

    override suspend fun execute(param: String): Flow<List<Post>> = repository.listPosts(param)
}