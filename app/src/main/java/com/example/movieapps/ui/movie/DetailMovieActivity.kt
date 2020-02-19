package com.example.movieapps.ui.movie

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
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.response.genres.GenreResponse
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity(),NetworkListener {
    lateinit var genreMovieViewModel: GenreMovieViewModel
    lateinit var genres:List<String>
    lateinit var menu: Menu
    lateinit var result: Result
    var favorite:Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_movie_menu,menu)
        Log.d("OnCreateOptionMenu","Menu Created")
        this.menu = menu!!
        val db = FavoriteDatabaseHelper.createDb(this)
        val fav = db.favMovieDao().getMovieByTitle(result.title)
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
        Log.d("OnCreate","Activity Created")
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Movie"
        genreMovieViewModel = ViewModelProvider(this,ViewModelFactory(Injection.provideRepository(this)))
            .get(GenreMovieViewModel::class.java)
        genreMovieViewModel.networkListener = this


        pb_loading_genre.visibility = View.VISIBLE
        result = intent.getParcelableExtra<Result>("movie_data")

        genres = result.genreIds
        Glide.with(this)
            .load(BuildConfig.IMAGE_URL + "/w185/" + result.posterPath)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(img_poster)

        tv_title.text = result.title
        tv_release.text = result.releaseDate
        tv_overview.text = result.overview
        genreMovieViewModel.setMovieGenre()
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
        genreMovieViewModel.getMovieGenre().observe(this,Observer<GenreResponse>{
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
                db.favMovieDao().deleteMovieFav(result.title)
                menu.getItem(0).setIcon(R.drawable.ic_favorite_border)
                Toast.makeText(this,"Deleted From Favorite",Toast.LENGTH_SHORT).show()
                favorite = false;
            }else{
                val result = intent.getParcelableExtra<Result>("movie_data")
                val favoriteMovieEntity = FavoriteMovieEntity(result.adult,result.backdropPath,result.genreIds,result.id,result.originalLanguage,
                    result.originalTitle,result.overview,result.popularity,result.posterPath,result.releaseDate,result.title,result.video,result.voteAverage,
                    result.voteCount)
                db.favMovieDao().addFavMovie(favoriteMovieEntity)
                menu.getItem(0).setIcon(R.drawable.ic_favorite)
                Toast.makeText(this,"Added To Favorite",Toast.LENGTH_SHORT).show()
                favorite = true
            }

            FavoriteMovieFragment.updateData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(message: String) {
        Log.d("Detail Movie",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,resources.getString(R.string.failure),Toast.LENGTH_SHORT).show()
    }
}
