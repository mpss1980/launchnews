package br.com.coupledev.launchnews.data.repository

import br.com.coupledev.launchnews.data.api.SpaceFlightNewsApi
import br.com.coupledev.launchnews.data.model.Launch
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(private val api: SpaceFlightNewsApi): PostRepository {
    override suspend fun listPosts(): Flow<List<Post>>  = flow {
        val postList = api.listPosts()
        emit(postList)
    }
}