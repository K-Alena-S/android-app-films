package com.example.android_app_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app_films.R
import com.example.android_app_films.ui.adapters.FilmAdapter
import com.example.android_app_films.viewmodel.FilmViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFilmsFragment : Fragment() {

    private val filmViewModel: FilmViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list_films, container, false)
        recyclerView = view.findViewById(R.id.moviesGrid)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        filmViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            progressBar.visibility = View.GONE
            if (films != null && films.isNotEmpty()) {
                recyclerView.adapter = FilmAdapter(films)
            } else {
                showError()
            }
        })

        if (filmViewModel.films.value == null) {
            filmViewModel.fetchFilms()
        }
    }

    private fun showError() {
        Snackbar.make(requireView(), R.string.error_network_text, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.return_text) {
                if (filmViewModel.films.value == null) {
                    filmViewModel.fetchFilms()
                }
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}