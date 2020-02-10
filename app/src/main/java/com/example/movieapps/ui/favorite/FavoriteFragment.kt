package com.example.movieapps.ui.favorite


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapps.R
import com.example.movieapps.ui.adapter.PageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    lateinit var pageAdapter: FragmentStateAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Favorit Fragment","Activity Created")
        view_pager.adapter = PageAdapter(this.activity!!)
        TabLayoutMediator(tabLayout,view_pager){
            tab, position ->
            if(position == 0)
                tab.text = "Favorite Movie"
            else if(position == 1){
                tab.text = "Favorite TV Show"
            }
        }.attach()
    }
}
