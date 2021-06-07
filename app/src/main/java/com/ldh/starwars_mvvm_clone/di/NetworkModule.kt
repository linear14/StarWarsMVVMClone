package com.ldh.starwars_mvvm_clone.di

import com.ldh.data_remote.api.StarWarsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideOkHttpClient() }

    // get() : 필요한 컴포넌트를 주입받음 (여기서는 okHttpClient를 찾겠지?)
    single { provideRetrofit(okHttpClient = get(), url = "https://swapi.dev/api/") }

    single { provideStarWarsService(get()) }

}

internal fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

internal fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

internal fun provideStarWarsService(retrofit: Retrofit): StarWarsApiService =
    retrofit.create(StarWarsApiService::class.java)