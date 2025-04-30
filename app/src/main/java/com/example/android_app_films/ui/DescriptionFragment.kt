package com.example.android_app_films.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.android_app_films.databinding.FragmentDescriptionBinding
import com.example.android_app_films.viewmodel.FilmViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val filmViewModel: FilmViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        binding.cinema.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmName = arguments?.getString("name") ?: return
        (activity as? MainActivity)?.setToolbarTitle(filmName)

        val filmId = arguments?.getInt("film_id") ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            val selectedFilm = filmViewModel.fetchFilmById(filmId)
            selectedFilm?.let {
                binding.filmTitle.text = it.localized_name
                binding.filmDescription.text = it.description
                binding.cinema.visibility = View.VISIBLE
                binding.filmGenresYear.text = it.genres.joinToString(", ") + ", " + it.year.toString() + " год"
                binding.filmRating.text = it.rating.toString()
                Glide.with(this@DescriptionFragment).load(it.image_url).into(binding.filmImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

