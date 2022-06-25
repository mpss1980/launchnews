package br.com.coupledev.launchnews.data.api

import br.com.coupledev.launchnews.data.model.Post
import retrofit2.http.GET

interface SpaceFlightNewsApi {

    @GET("articles")
    suspend fun listPosts(): List<Post>
}