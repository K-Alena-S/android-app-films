package com.example.android_app_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_app_films.R
import com.example.android_app_films.databinding.FragmentListFilmsBinding
import com.example.android_app_films.ui.adapters.FilmAdapter
import com.example.android_app_films.ui.adapters.GenreAdapter
import com.example.android_app_films.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.android_app_films.models.data.Film
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ListFilmsFragment : Fragment() {

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    private val filmViewModel: FilmViewModel by viewModel()
    private var currentGenre: String? = null // Переменная для хранения текущего жанра

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE

        if (savedInstanceState != null) {
            currentGenre = savedInstanceState.getString(KEY_CURRENT_GENRE)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                filmViewModel.fetchFilms()
            } catch (e: Exception) {
                showError()
            }
        }

        filmViewModel.films.observe(viewLifecycleOwner) { films ->
            binding.progressBar.visibility = View.GONE
            setupGenres(films)
            updateMovies(films)
        }
    }

    private fun showError() {
        Snackbar.make(requireView(), R.string.error_network_text, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.return_text) {
                binding.progressBar.visibility = View.VISIBLE
            }.show()
    }

    private fun setupGenres(films: List<Film>) {
        val genres = films.flatMap { it.genres }.distinct().sorted()
        binding.genresRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.genresRecyclerView.adapter = GenreAdapter(genres, currentGenre) { genre ->
            toggleGenreFilter(genre, films)
        }
    }

    private fun updateMovies(films: List<Film>) {
        val filteredFilms = if (currentGenre != null) {
            films.filter { it.genres.contains(currentGenre!!) }
        } else {
            films
        }

        val sortedFilms = filteredFilms.sortedBy { it.localized_name }

        binding.moviesGrid.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.moviesGrid.adapter = FilmAdapter(sortedFilms) { filmId ->
            val bundle = Bundle().apply {
                putInt("film_id", filmId)
            }
            findNavController().navigate(R.id.action_ListFilmsFragment_to_DescriptionFragment, bundle)
        }
    }

    private fun toggleGenreFilter(genre: String, films: List<Film>) {
        if (currentGenre == genre) {
            currentGenre = null
        } else {
            currentGenre = genre
        }

        updateMovies(films)
        setupGenres(films)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_GENRE, currentGenre)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_CURRENT_GENRE = "current_genre"
    }
}