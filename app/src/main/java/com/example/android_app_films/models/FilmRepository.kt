package com.example.android_app_films.models

import com.example.android_app_films.models.data.Film
import com.example.android_app_films.models.data.FilmResponse

class FilmRepository(private val filmApiService: FilmApiService) {
    suspend fun getFilms(): FilmResponse {
        return filmApiService.getFilms()
    }

    suspend fun getFilmById(id: Int): Film? {
        val response = getFilms()
        return response.films.find { it.id == id }
    }
}

