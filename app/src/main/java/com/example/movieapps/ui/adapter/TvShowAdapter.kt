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
import com.example.movieapps.data.response.tv_show.TvShowResponse
import com.example.movieapps.ui.tv_show.DetailTvShowActivity
import kotlinx.android.synthetic.main.tvshow_item_layout.view.*

class TvShowAdapter(private val context: Context, result: ArrayList<Result>) :
    RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    var results = result

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.tvshow_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        Glide.with(context)
            .load(BuildConfig.IMAGE_URL + "/w185/" + results[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.img_poster)

        holder.tv_movie_title.text = results[position].name
        holder.tv_rating.text = results[position].voteAverage.toString()
        holder.tv_popularity.text = "Popularity : " + results[position].popularity
        holder.btn_see_more.setOnClickListener { view ->
            var intent = Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra("tv_show_data", results[position])
            context.startActivity(intent)
        }
    }

    fun setItems(results: ArrayList<Result>) {
        this.results = results
    }

    class TvShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_poster = view.img_poster
        val tv_movie_title = view.tv_title
        val tv_rating = view.tv_rating
        val tv_popularity = view.tv_popularity
        val btn_see_more = view.btn_see_more
    }
}