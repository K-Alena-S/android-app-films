package com.example.android_app_films.models

import com.example.android_app_films.models.data.FilmResponse

class FilmRepository(private val filmApiService: FilmApiService) {
    suspend fun getFilms(): FilmResponse {
        return filmApiService.getFilms()
    }
}
