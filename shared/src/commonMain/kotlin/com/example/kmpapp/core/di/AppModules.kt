package com.example.kmpapp.core.di

import com.example.kmpapp.core.network.EmptyTokenProvider
import com.example.kmpapp.core.network.TokenProvider
import com.example.kmpapp.core.network.createHttpClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single<TokenProvider> { EmptyTokenProvider() }
    single<HttpClient> { createHttpClient(get()) }
    single { com.example.kmpapp.core.network.NetworkHelper(get()) }
}

val appModule: Module = module {
    includes(networkModule)
}
