package com.example.android_app_films.di

import com.example.android_app_films.models.FilmApiService
import com.example.android_app_films.models.FilmRepository
import com.example.android_app_films.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { createWebService<FilmApiService>() }
    single { FilmRepository(get()) }
    viewModel { FilmViewModel(get()) }
}

inline fun <reified T> createWebService(): T {
    return Retrofit.Builder()
        .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)
}
