package br.com.coupledev.launchnews.domain.repository

import br.com.coupledev.launchnews.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts(): Flow<List<Post>>
}