package com.example.movieapps.ui.tv_show

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.R
import com.example.movieapps.data.database.FavoriteDatabaseHelper
import com.example.movieapps.data.entity.FavoriteTvShowEntity
import com.example.movieapps.data.response.genres.GenreResponse
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : AppCompatActivity(),NetworkListener {
    lateinit var genreTvShowViewModel: GenreTvShowViewModel
    lateinit var genres:List<String>
    lateinit var menu: Menu
    lateinit var result: Result
    var favorite:Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_movie_menu,menu)
        Log.d("OnCreateOptionMenu","Menu Created")
        this.menu = menu!!
        val db = FavoriteDatabaseHelper.createDb(this)
        val fav = db.favTvShowDao().getTvShowByName(result.name)
        if(fav.isNotEmpty()){
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite)
            favorite = true
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.add_to_favorite)?.isVisible = tv_genre.visibility == View.VISIBLE
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Tv Show"
        genreTvShowViewModel = ViewModelProvider(this,
            ViewModelFactory(Injection.provideRepository(this))
        )
            .get(GenreTvShowViewModel::class.java)

        pb_loading_genre.visibility = View.VISIBLE
        result = intent.getParcelableExtra<Result>("tv_show_data")
        genres = result.genreIds
        Glide.with(this)
            .load(BuildConfig.IMAGE_URL + "/w185/" + result.posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(img_poster)

        tv_title.text = result.name
        tv_release.text = result.firstAirDate
        tv_overview.text = result.overview
        genreTvShowViewModel.setTvShowGenre()
        initObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun hideAndShow(hide:Boolean){
        if(hide){
            img_poster.visibility = View.INVISIBLE
            tv_title.visibility = View.INVISIBLE
            tv_genre.visibility = View.INVISIBLE
            tv_release.visibility = View.INVISIBLE
            tv_overview.visibility = View.INVISIBLE
            tv_lblGenre.visibility = View.INVISIBLE
            tv_lblOverview.visibility = View.INVISIBLE
            tv_lblRelease.visibility = View.INVISIBLE
        }else{
            img_poster.visibility = View.VISIBLE
            tv_title.visibility = View.VISIBLE
            tv_genre.visibility = View.VISIBLE
            tv_release.visibility = View.VISIBLE
            tv_overview.visibility = View.VISIBLE
            tv_lblGenre.visibility = View.VISIBLE
            tv_lblOverview.visibility = View.VISIBLE
            tv_lblRelease.visibility = View.VISIBLE
        }
    }

    fun initObserver(){
        genreTvShowViewModel.getTvShowGenre().observe(this, Observer<GenreResponse>{
                response->
            if(response != null){
                var genreList = ""
                for (i in 0 until genres.size){
                    for(j in 0 until response.genres.size){
                        if(genres[i] == response.genres[j].id.toString()){
                            genreList += response.genres[j].name + ","
                        }
                    }
                }

                tv_genre.text = genreList.substring(0,genreList.length-1)
                hideAndShow(false)
            }else{
                hideAndShow(true)
            }

            pb_loading_genre.visibility = View.INVISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_to_favorite){
            val db = FavoriteDatabaseHelper.createDb(this)

            if(favorite){
                db.favTvShowDao().deleteTvShow(result.name)
                menu.getItem(0).setIcon(R.drawable.ic_favorite_border)
                Toast.makeText(this,"Deleted From Favorite",Toast.LENGTH_SHORT).show()
                favorite = false
            }else{
                val result = intent.getParcelableExtra<Result>("tv_show_data")
                val favoriteTvShowEntity = FavoriteTvShowEntity(result.backdropPath,result.firstAirDate,result.genreIds,result.id,
                    result.name,result.originCountry,result.originalLanguage,result.originalName,result.overview,result.popularity,
                    result.posterPath,result.voteAverage,result.voteCount)
                db.favTvShowDao().addFavTvShow(favoriteTvShowEntity)
                menu.getItem(0).setIcon(R.drawable.ic_favorite)
                Toast.makeText(this,"Added To Favorite",Toast.LENGTH_SHORT).show()
                favorite = true
            }
            return true
        }
        FavoriteTvShowFragment.updateData()
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(message: String) {
        Log.d("Detail Tv Show",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,resources.getString(R.string.failure),Toast.LENGTH_SHORT).show()
    }
}
