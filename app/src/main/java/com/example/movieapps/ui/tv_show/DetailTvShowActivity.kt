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
import com.example.movieapps.R
import com.example.movieapps.data.database.DatabaseHelper
import com.example.movieapps.data.response.genres.GenreResponse
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : AppCompatActivity() {
    lateinit var genreTvShowViewModel: GenreTvShowViewModel
    lateinit var genres:List<String>
    lateinit var menu: Menu
    lateinit var result: Result
    var favorite:Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_movie_menu,menu)
        Log.d("OnCreateOptionMenu","Menu Created")
        this.menu = menu!!
        val db = DatabaseHelper.createDb(this)
        val fav = db.favTvShowDao().getTvShowByName(result.name)
        if(fav.isNotEmpty()){
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite)
            favorite = true
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Movie"
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

    fun initObserver(){
        genreTvShowViewModel.getTvShowGenre().observe(this, Observer<GenreResponse>{
                response->
            var genreList = ""
            for (i in 0 until genres.size){
                for(j in 0 until response.genres.size){
                    if(genres[i] == response.genres[j].id.toString()){
                        genreList += response.genres[j].name + ","
                    }
                }
            }

            tv_genre.text = genreList.substring(0,genreList.length-1)

            pb_loading_genre.visibility = View.INVISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_to_favorite){
            val db = DatabaseHelper.createDb(this)

            if(favorite){
                db.favTvShowDao().deleteTvShow(result.name)
                menu.getItem(0).setIcon(R.drawable.ic_favorite_border)
                Toast.makeText(this,"Deleted From Favorite",Toast.LENGTH_SHORT).show()
            }else{
                db.favTvShowDao().addFavTvShow(intent.getParcelableExtra<Result>("tv_show_data"))
                menu.getItem(0).setIcon(R.drawable.ic_favorite)
                Toast.makeText(this,"Added To Favorite",Toast.LENGTH_SHORT).show()
            }
            return true
        }
        FavoriteTvShowFragment.updateData()
        return super.onOptionsItemSelected(item)
    }
}
