package br.com.coupledev.launchnews.presentation.di

import br.com.coupledev.launchnews.presentation.ui.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModuleModule())
    }

    private fun viewModuleModule(): Module {
        return module {
            factory { HomeViewModel(get()) }
        }
    }
}