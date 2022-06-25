package br.com.coupledev.launchnews.data.di

import br.com.coupledev.launchnews.data.repository.MockAPIService
import br.com.coupledev.launchnews.data.repository.PostRepositoryImpl
import br.com.coupledev.launchnews.domain.repository.PostRepository
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule {

    fun load() {
        loadKoinModules(postModule())
    }

    private fun postModule(): Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(MockAPIService) }
        }
    }
}