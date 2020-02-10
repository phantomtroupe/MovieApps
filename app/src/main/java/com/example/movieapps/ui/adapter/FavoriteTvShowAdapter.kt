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
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.ui.tv_show.DetailTvShowActivity
import kotlinx.android.synthetic.main.tvshow_item_layout.view.*

class FavTvShowAdapter(private val context:Context, private val result:Array<Result>) : RecyclerView.Adapter<FavTvShowAdapter.FavTvShowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTvShowViewHolder {
        return FavTvShowViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.tvshow_item_layout, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: FavTvShowViewHolder, position: Int) {
        Glide.with(context)
            .load(BuildConfig.IMAGE_URL + "/w185/" + result[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.img_poster)

        holder.tv_movie_title.text = result[position].name
        holder.tv_rating.text = result[position].voteAverage.toString()
        holder.tv_popularity.text = "Popularity : ${result[position].popularity}"
        holder.btn_see_more.setOnClickListener {
            val intent = Intent(context,DetailTvShowActivity::class.java)
            intent.putExtra("tv_show_data",result[position])
            context.startActivity(intent)
        }
    }

    class FavTvShowViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val img_poster = view.img_poster
        val tv_movie_title = view.tv_title
        val tv_rating = view.tv_rating
        val tv_popularity = view.tv_popularity
        val btn_see_more = view.btn_see_more
    }
}