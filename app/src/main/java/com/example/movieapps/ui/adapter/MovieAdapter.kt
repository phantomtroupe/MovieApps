package com.example.movieapps.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig
import com.example.movieapps.R
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.ui.movie.DetailMovieActivity
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieAdapter(private val context:Context, private val movieResponse:MovieResponse) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.movie_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movieResponse.results.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(context)
            .load(BuildConfig.IMAGE_URL + "/w185/" + movieResponse.results[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.img_poster)

        holder.tv_movie_title.text = movieResponse.results[position].title
        holder.tv_rating.text = movieResponse.results[position].voteAverage
        holder.tv_popularity.text = "Popularity : ${movieResponse.results[position].popularity}"
        holder.btn_see_more.setOnClickListener {view->
            val intent = Intent(context,DetailMovieActivity::class.java)
            intent.putExtra("movie_data",movieResponse.results[position])
            context.startActivity(intent)
        }
    }

    class MovieViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val img_poster = view.img_poster
        val tv_movie_title = view.tv_title
        val tv_rating = view.tv_rating
        val tv_popularity = view.tv_popularity
        val btn_see_more = view.btn_see_more
    }
}