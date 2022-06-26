package br.com.coupledev.launchnews.data.repository

import br.com.coupledev.launchnews.core.RemoteException
import br.com.coupledev.launchnews.data.api.SpaceFlightNewsApi
import br.com.coupledev.launchnews.data.model.Launch
import br.com.coupledev.launchnews.data.model.Post
import br.com.coupledev.launchnews.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostRepositoryImpl(private val api: SpaceFlightNewsApi) : PostRepository {
    override suspend fun listPosts(category: String): Flow<List<Post>> = flow {
        try {
            val postList = api.listPosts(category)
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to connect to SpaceFlightNews API")
        }
    }
}