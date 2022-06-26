package br.com.coupledev.launchnews.domain.usecases

import br.com.coupledev.launchnews.configureTestAppComponent
import br.com.coupledev.launchnews.data.enums.SpaceFlightNewsCategory
import br.com.coupledev.launchnews.data.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class GetLatestPostUseCaseTest : KoinTest {

    val getLatestPostUseCase: GetLatestPostUseCase by inject()

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
            val result = getLatestPostUseCase(SpaceFlightNewsCategory.ARTICLES.value)
            assertNotNull(result)
        }
    }

    @Test
    fun shouldReturnACorrectObjectTypeFromRepository() {
        runBlocking {
            val result = getLatestPostUseCase(SpaceFlightNewsCategory.ARTICLES.value)
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun shouldReturnNotEmptyFromRepository() {
        runBlocking {
            val result = getLatestPostUseCase(SpaceFlightNewsCategory.ARTICLES.value)
            assertFalse(result.first().isEmpty())
        }
    }

}