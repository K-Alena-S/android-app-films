package com.example.android_app_films.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_app_films.models.data.Film
import com.example.android_app_films.models.FilmRepository

class FilmViewModel(private val repository: FilmRepository) : ViewModel() {
    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> get() = _films

    suspend fun fetchFilms() {
        if (_films.value == null) {
            try {
                val response = repository.getFilms()
                _films.value = response.films
            } catch (e: Exception) {
                _films.value = emptyList()
            }
        }
    }
}

