package com.example.android_app_films.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app_films.R
import com.example.android_app_films.models.data.Film
import com.bumptech.glide.Glide


class FilmAdapter(private val films: List<Film>) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
        private val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)

        fun bind(film: Film) {
            filmTitle.text = film.localized_name
            Glide.with(itemView.context)
                .load(film.image_url)
                .into(filmImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_films, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int {
        return films.size
    }
}
