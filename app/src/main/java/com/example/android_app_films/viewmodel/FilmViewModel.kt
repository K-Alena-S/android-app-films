package com.example.android_app_films.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_app_films.models.data.Film
import com.example.android_app_films.models.FilmRepository

class FilmViewModel(private val repository: FilmRepository) : ViewModel() {
    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> get() = _films

    val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    suspend fun fetchFilms(): Boolean {
        return try {
            val response = repository.getFilms()
            _films.value = response.films
            _error.value = null
            true
        } catch (e: Exception) {
            _films.value = emptyList()
            _error.value = e.message
            false
        }
    }

    suspend fun fetchFilmById(id: Int): Film? {
        return try {
            repository.getFilmById(id)
        } catch (e: Exception) {
            null
        }
    }
}