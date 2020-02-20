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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.R
import com.example.movieapps.data.database.FavoriteDatabaseHelper
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.data.response.tv_show.TvShowResponse
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.TvShowAdapter
import com.example.movieapps.ui.movie.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.refresh_layout
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment(), NetworkListener, SwipeRefreshLayout.OnRefreshListener {

    var results = ArrayList<Result>()
    var isLoading = false

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
    var tvShowAdapter:TvShowAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvShowViewModel = ViewModelProvider(this, Injection.provideViewModelFractory(this.context!!))
            .get(TvShowViewModel::class.java)

        tvShowViewModel.networkListener = this

        rv_tvshow.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!isLoading){
                    if(!recyclerView.canScrollVertically((1))){
                        val listTvShow = FavoriteDatabaseHelper.createDb(context!!).tvShowDao().getTvShows()
                        val page = listTvShow[listTvShow.size - 1].page + 1
                        Log.e("MovieFragment",page.toString())
                        updateTvShow(page)
                    }
                }
            }
        })

        initObserver()
        updateTvShow()
    }

    fun updateTvShow(page:Int = 1){
        refresh_layout.setOnRefreshListener(this)
        refresh_layout.isRefreshing=true
        tvShowViewModel.setTvShow(page)
    }

    fun initObserver(){
        tvShowViewModel.getTvShow().observe(this, Observer<ArrayList<TvShowResponse>>{
                response ->
            results.clear()
            for(i in 0 until response.size){
                for(j in 0 until response[i].results.size){
                    results.add(response[i].results[j])
                }
            }

            if(tvShowAdapter != null){
                tvShowAdapter?.setItems(results)
                tvShowAdapter?.notifyDataSetChanged()
                Log.e("TvShowFragment","NotifyDataSetChanged")
            }else{
                tvShowAdapter = TvShowAdapter(this.context!!, results)
                rv_tvshow.layoutManager = LinearLayoutManager(this.context!!)
                rv_tvshow.adapter = tvShowAdapter
            }
            refresh_layout.isRefreshing = false
            isLoading = false
        })
    }
}
