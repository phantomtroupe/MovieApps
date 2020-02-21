package com.example.movieapps.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.R
import com.example.movieapps.data.dao.MovieDao
import com.example.movieapps.data.database.FavoriteDataBase
import com.example.movieapps.data.database.FavoriteDatabaseHelper
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), NetworkListener, SwipeRefreshLayout.OnRefreshListener {

    var results = ArrayList<Result>()
    var isLoading = false

    override fun onRefresh() {
        movieViewModel.setMovies()
    }

    override fun onSuccess(message: String) {
        Log.d("Get Movie",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity,resources.getString(R.string.failure),Toast.LENGTH_SHORT).show()
        Log.d("Get Movie",message)
        refresh_layout.isRefreshing=false
    }
    lateinit var movieViewModel: MovieViewModel
    var movieAdapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Movie Fragment","View Created")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("ActivityCreated","Ulala")

        movieViewModel = ViewModelProvider(this,Injection.provideViewModelFractory(this.context!!))
            .get(MovieViewModel::class.java)

        movieViewModel.networkListener = this


        rv_movies.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!isLoading){
                    if(!recyclerView.canScrollVertically((1))){
                        val listMovie = FavoriteDatabaseHelper.createDb(context!!).movieDao().getMovies()
                        if(listMovie.size > 0){
                            val page = listMovie[listMovie.size - 1].page + 1
                            Log.e("MovieFragment",page.toString())
                            updateMovie(page)
                        }
                    }
                }
            }
        })


        initObserver()
        updateMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Fragment","Destroyed")
    }

    fun updateMovie(page:Int = 1){
        refresh_layout.setOnRefreshListener(this)
        refresh_layout.isRefreshing=true
        movieViewModel.setMovies(page)
    }

    fun initObserver(){
        movieViewModel.getMovies().observe(this, Observer<ArrayList<MovieResponse>>{
            response ->
            results.clear()
            for(i in 0 until response.size){
                for(j in 0 until response[i].results.size){
                    results.add(response[i].results[j])
                }
            }

            if(movieAdapter != null){
                movieAdapter?.setItems(results)
                movieAdapter?.notifyDataSetChanged()
                Log.e("MovieFragment","NotifyDataSetChanged")
            }else{
                movieAdapter = MovieAdapter(this.context!!, results)
                rv_movies.layoutManager = LinearLayoutManager(this.context!!)
                rv_movies.adapter = movieAdapter
            }
            refresh_layout.isRefreshing = false
            isLoading = false
        })
    }

}
