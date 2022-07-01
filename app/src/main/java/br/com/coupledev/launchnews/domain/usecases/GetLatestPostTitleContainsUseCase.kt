package br.com.coupledev.launchnews.domain.usecases

import br.com.coupledev.launchnews.core.Query
import br.com.coupledev.launchnews.core.Usecase
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostTitleContainsUseCase(private val repository: PostRepository) : Usecase<Query, List<Post>>() {

    override suspend fun execute(param: Query): Flow<List<Post>> =
        repository.listPostsTitleContains(param.type, param.option)
}