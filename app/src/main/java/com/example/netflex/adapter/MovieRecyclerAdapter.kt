package com.example.netflex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflex.databinding.RecyclerSampleItemBinding
import com.example.netflex.model.ApiResponse
import com.example.netflex.utils.RetrofitConstants
import com.example.netflex.utils.loadImageResWithGlide

class MovieRecyclerAdapter(
    private var response: ApiResponse?,
    private val callBack: (page: Int) -> Unit
) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: RecyclerSampleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var data: MutableList<ApiResponse.Movie>? = response?.results

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
        data ?: return
        val iv = holder.binding.ivPoster
        val uri = RetrofitConstants.IMAGE_BASE_URL + data!![position].poster_path
        iv.loadImageResWithGlide(holder.itemView.context, uri)

        if (position == data!!.size - 1) {
            callBack(response?.page!!)
        }

    }

    override fun getItemCount(): Int {
        data ?: return 0
        return data!!.size
    }

    // used for filtering
    @SuppressLint("NotifyDataSetChanged") // data set is being changed completely
    fun setData(response: ApiResponse?){
        this.response = response
        this.data = response?.results
        notifyDataSetChanged()
    }

    // used for paging
    @SuppressLint("NotifyDataSetChanged") // data set is being changed completely
    fun addData(response: ApiResponse?){ // TODO: Remove function after configuring data flow from viewmodel
        response?: return
        this.data?.addAll(response.results!!)
        this.response = response
        notifyDataSetChanged()
    }
}