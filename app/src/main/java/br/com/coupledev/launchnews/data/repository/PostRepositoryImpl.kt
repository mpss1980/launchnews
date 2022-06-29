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
            val postList = api.listPosts(type = category)
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts!")
        }
    }

    override suspend fun listPostsTitleContains(category: String, titleContains: String?): Flow<List<Post>> = flow {
        try {
            val postList = api.listPostsTitleContains(type = category, titleContains = titleContains)
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts!")
        }
    }
}