package com.example.android_app_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app_films.R
import com.example.android_app_films.ui.adapters.FilmAdapter
import com.example.android_app_films.ui.adapters.GenreAdapter
import com.example.android_app_films.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.android_app_films.models.data.Film

class ListFilmsFragment : Fragment() {

    private val filmViewModel: FilmViewModel by viewModel()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var genresRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var currentGenre: String? = null // Переменная для хранения текущего жанра

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list_films, container, false)
        moviesRecyclerView = view.findViewById(R.id.moviesGrid)
        genresRecyclerView = view.findViewById(R.id.genresRecyclerView)
        moviesRecyclerView.setNestedScrollingEnabled(false)
        genresRecyclerView.setNestedScrollingEnabled(false)
        progressBar = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        filmViewModel.fetchFilms()

        filmViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            progressBar.visibility = View.GONE
            setupGenres(films)
            setupMovies(films)
        })
    }

    private fun setupGenres(films: List<Film>) {
        val genres = films.flatMap { it.genres }.distinct().sorted()
        genresRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        genresRecyclerView.adapter = GenreAdapter(genres) { genre ->
            toggleGenreFilter(genre, films)
        }
    }

    private fun setupMovies(films: List<Film>) {
        val sortedFilms = films.sortedBy { it.localized_name }

        moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        moviesRecyclerView.adapter = FilmAdapter(sortedFilms)
    }

    private fun toggleGenreFilter(genre: String, films: List<Film>) {
        if (currentGenre == genre) {
            currentGenre = null
            setupMovies(films)
        } else {
            currentGenre = genre
            val filteredFilms = films.filter { it.genres.contains(genre) }
            moviesRecyclerView.adapter = FilmAdapter(filteredFilms)
        }
    }
}
