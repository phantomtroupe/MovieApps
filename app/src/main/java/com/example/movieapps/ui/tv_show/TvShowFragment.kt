package com.example.movieapps.ui.tv_show


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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.R
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.TvShowAdapter
import com.example.movieapps.ui.movie.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.refresh_layout
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment(), NetworkListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        tvShowViewModel.setTvShow()
    }

    override fun onSuccess(message: String) {
        Log.d("Get Tv Show",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity,resources.getString(R.string.failure), Toast.LENGTH_SHORT).show()
        Log.d("Get Tv Show",message)
        refresh_layout.isRefreshing=false
    }
    lateinit var tvShowViewModel: TvShowViewModel
    lateinit var tvShowAdapter:TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvShowViewModel = ViewModelProvider(this, Injection.provideViewModelFractory())
            .get(TvShowViewModel::class.java)
        tvShowViewModel.networkListener = this
        initObserver()
        refresh_layout.setOnRefreshListener(this)
        refresh_layout.isRefreshing=true
        updateTvShow()
        Log.e("ActivityCreated","")
    }

    fun updateTvShow(){
        tvShowViewModel.setTvShow()
    }

    fun initObserver(){
        tvShowViewModel.getTvShow().observe(this, Observer {
            response->
            Log.d("Tv Show Response",response.results.toString())
            tvShowAdapter = TvShowAdapter(context!!,response)
            rv_tvshow.layoutManager = LinearLayoutManager(context!!)
            rv_tvshow.adapter = tvShowAdapter
            refresh_layout.isRefreshing = false
        })
    }
}
