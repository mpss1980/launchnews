package br.com.coupledev.launchnews

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(configureDataModelForTest(BASE_URL) + configureDomainModulesForTest())
}