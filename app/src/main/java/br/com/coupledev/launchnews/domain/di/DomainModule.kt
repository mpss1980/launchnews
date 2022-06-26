package br.com.coupledev.launchnews.domain.di

import br.com.coupledev.launchnews.domain.usecases.GetLatestPostUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostUseCase(get()) }
        }
    }
}