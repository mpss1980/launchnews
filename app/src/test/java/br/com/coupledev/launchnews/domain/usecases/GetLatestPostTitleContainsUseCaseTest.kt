package br.com.coupledev.launchnews.domain.usecases

import br.com.coupledev.launchnews.configureTestAppComponent
import br.com.coupledev.launchnews.core.Query
import br.com.coupledev.launchnews.data.enums.SpaceFlightNewsCategory
import br.com.coupledev.launchnews.data.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GetLatestPostTitleContainsUseCaseTest : KoinTest {

    val getLatestPostTitleContainsUseCase: GetLatestPostTitleContainsUseCase by inject()
    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val searchString = "mars"

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            configureTestAppComponent()
        }

        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }

    @Test
    fun shouldReturnNotNullConnectingWithRepository() {
        runBlocking {
            val result = getLatestPostTitleContainsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))
            assertNotNull(result)
        }
    }

    @Test
    fun shouldReturnACorrectObjectTypeFromRepository() {
        runBlocking {
            val result = getLatestPostTitleContainsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun shouldReturnNotEmptyFromRepository() {
        runBlocking {
            val result = getLatestPostTitleContainsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))
            assertFalse(result.first().isEmpty())
        }
    }

    @Test
    fun shouldReturnValidResultsWhenItSearches() {
        runBlocking {
            val result = getLatestPostTitleContainsUseCase(Query(type, searchString))
            var assertion = true
            result.first().forEach { post ->
                assertion = assertion && post.title.lowercase().contains(searchString)
            }
            assertTrue(assertion)
        }
    }

}