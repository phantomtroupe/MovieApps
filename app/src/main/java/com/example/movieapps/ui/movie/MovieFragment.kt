package com.example.movieapps.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.Network.Routes
import com.example.movieapps.R
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.di.Injection

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment(), NetworkListener {
    override fun onSuccess(message: String) {
        Log.d("Get Movie",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity,resources.getString(R.string.failure),Toast.LENGTH_SHORT).show()
        Log.d("Get Movie",message)
    }

    lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
