package com.example.netflex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflex.databinding.RecyclerSampleItemBinding
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.model.MovieEntity
import com.example.netflex.utils.RetrofitConstants
import com.example.netflex.utils.loadImage

class MovieRecyclerAdapter(
    private var response: ApiResponse?,
    private val callBack: (page: Int) -> Unit
) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: RecyclerSampleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var data = mutableListOf<MovieEntity>()

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
        val iv = holder.binding.ivPoster
        val uri = RetrofitConstants.IMAGE_BASE_URL + data[position].poster_path
        iv.loadImage(holder.itemView.context, uri)

        if (position == data.size - 1) {
            callBack(response?.page!! + 1)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged") // data set is being changed completely
    fun setData(response: ApiResponse?, movies: MutableList<MovieEntity>){
        this.response = response
        this.data = movies
        notifyDataSetChanged()
    }

}