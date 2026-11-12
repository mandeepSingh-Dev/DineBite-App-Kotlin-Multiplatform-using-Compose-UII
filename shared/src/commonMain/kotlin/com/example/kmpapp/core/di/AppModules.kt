package com.example.kmpapp.core.di

import com.example.kmpapp.core.datastore.AppDatastore
import com.example.kmpapp.core.datastore.AppDatastoreImpl
import com.example.kmpapp.core.datastore.createDataStore
import com.example.kmpapp.core.network.EmptyTokenProvider
import com.example.kmpapp.core.network.NetworkHelper
import com.example.kmpapp.core.network.TokenProvider
import com.example.kmpapp.core.network.apis.DineBiteApi
import com.example.kmpapp.core.network.createHttpClient
import com.example.kmpapp.dinebite.ui.LoginViewModel
import com.example.kmpapp.dinebite.ui.SignupViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val networkModule: Module = module {
    single<TokenProvider> { EmptyTokenProvider() }
    single<HttpClient> { createHttpClient(get()) }
    single { NetworkHelper(get()) }
    single { DineBiteApi(get(), get()) }
}

val datastoreModule: Module = module {
    single<AppDatastore> {
        AppDatastoreImpl(dataStore = createDataStore())
    }
}

val viewModelModule: Module = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignupViewModel(get(), get()) }

}

val appModule: Module = module {
    includes(networkModule, datastoreModule, viewModelModule)
}
