package br.com.coupledev.launchnews.data.di

import android.util.Log
import br.com.coupledev.launchnews.data.api.SpaceFlightNewsApi
import br.com.coupledev.launchnews.data.repository.PostRepositoryImpl
import br.com.coupledev.launchnews.domain.repository.PostRepository
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    private const val OK_HTTP = "OK http"

    fun load() {
        loadKoinModules(postModule() + networkModule())
    }

    private fun postModule(): Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(get()) }
        }
    }

    private fun networkModule(): Module {
        return module {
            single<SpaceFlightNewsApi> { createApi(get(), get()) }

            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }

                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
        }
    }

    private inline fun <reified T> createApi(factory: Moshi, client: OkHttpClient): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)
    }
}