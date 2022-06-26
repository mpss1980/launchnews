package br.com.coupledev.launchnews.data.api

import br.com.coupledev.launchnews.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightNewsApi {

    @GET("{type}")
    suspend fun listPosts(@Path("type") type: String): List<Post>
}