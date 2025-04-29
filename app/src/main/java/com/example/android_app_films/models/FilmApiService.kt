package com.example.android_app_films.models

import com.example.android_app_films.models.data.FilmResponse
import retrofit2.http.GET

interface FilmApiService {
    @GET("films.json")
    suspend fun getFilms(): FilmResponse
}