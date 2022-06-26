package br.com.coupledev.launchnews.data

import br.com.coupledev.launchnews.data.api.SpaceFlightNewsApi
import br.com.coupledev.launchnews.data.enums.SpaceFlightNewsCategory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SpaceFlightNewsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SpaceFlightNewsApi

    @Before
    fun createApi() {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldCallCorrectEndpointAccordingToParameterReceived() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result1 = api.listPosts(SpaceFlightNewsCategory.ARTICLES.value)
            val request1 = mockWebServer.takeRequest()
            assertEquals(request1.path, "/articles")

            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result2 = api.listPosts(SpaceFlightNewsCategory.BLOGS.value)
            val request2 = mockWebServer.takeRequest()
            assertEquals(request2.path, "/blogs")

            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result3 = api.listPosts(SpaceFlightNewsCategory.REPORTS.value)
            val request3 = mockWebServer.takeRequest()
            assertEquals(request3.path, "/reports")
        }
    }
}