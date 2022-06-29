package br.com.coupledev.launchnews.domain.repository

import br.com.coupledev.launchnews.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts(category: String): Flow<List<Post>>
    suspend fun listPostsTitleContains(category: String, titleContains: String?): Flow<List<Post>>
}