package com.example.android_app_films.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_app_films.models.data.Film
import com.example.android_app_films.models.FilmRepository
import kotlinx.coroutines.launch

class FilmViewModel(private val repository: FilmRepository) : ViewModel() {
    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> get() = _films

    private val _filteredFilms = MutableLiveData<List<Film>>()

    fun fetchFilms() {
        viewModelScope.launch {
            val response = repository.getFilms()
            _films.value = response.films
            _filteredFilms.value = response.films
        }
    }
}

