package br.com.coupledev.launchnews

import br.com.coupledev.launchnews.data.api.SpaceFlightNewsApi
import br.com.coupledev.launchnews.data.repository.PostRepositoryImpl
import br.com.coupledev.launchnews.domain.repository.PostRepository
import br.com.coupledev.launchnews.domain.usecases.GetLatestPostUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModulesForTest() = module {
    factory<GetLatestPostUseCase> { GetLatestPostUseCase(get()) }
}

fun configureDataModelForTest(baseUrl: String) = module {
    single {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsApi::class.java)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }
}