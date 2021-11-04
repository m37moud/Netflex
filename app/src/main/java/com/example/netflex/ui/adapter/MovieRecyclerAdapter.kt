package com.example.netflex.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflex.databinding.RecyclerSampleItemBinding
import com.example.netflex.model.Movie
import com.example.netflex.utils.loadImage

class MovieRecyclerAdapter(
    private val onMovieClick: (movie: Movie) -> Unit
) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(binding: RecyclerSampleItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            val poster = binding.ivPoster
        }

    private var data = mutableListOf<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            RecyclerSampleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.poster.loadImage(holder.itemView.context, data[position].generateImageUrl())
        holder.itemView.setOnClickListener { onMovieClick(data[position]) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movies: MutableList<Movie>){
        this.data = movies
        notifyDataSetChanged()
    }

}