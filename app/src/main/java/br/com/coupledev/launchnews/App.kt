package br.com.coupledev.launchnews

import android.app.Application
import br.com.coupledev.launchnews.data.di.DataModule
import br.com.coupledev.launchnews.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        PresentationModule.load()
        DataModule.load()
    }
}