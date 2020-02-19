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
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.ui.movie.DetailMovieActivity
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class FavMovieAdapter(private val context:Context, private val result:Array<FavoriteMovieEntity>) : RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        return FavMovieViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.movie_item_layout, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        Glide.with(context)
            .load(BuildConfig.IMAGE_URL + "/w185/" + result[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.img_poster)

        holder.tv_movie_title.text = result[position].title
        holder.tv_rating.text = result[position].voteAverage
        holder.tv_popularity.text = "Popularity : ${result[position].popularity}"
        holder.btn_see_more.setOnClickListener {
            val intent = Intent(context,DetailMovieActivity::class.java)
            intent.putExtra("movie_data",result[position])
            context.startActivity(intent)
        }
    }

    class FavMovieViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val img_poster = view.img_poster
        val tv_movie_title = view.tv_title
        val tv_rating = view.tv_rating
        val tv_popularity = view.tv_popularity
        val btn_see_more = view.btn_see_more
    }
}