package com.example.dicoding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding.Adapter.CustomAdapterTvFavofite
import com.example.dicoding.Model.Tv
import com.example.dicoding.db.MappingTvshowsHelper
import com.example.dicoding.db.TvshowsHelper
import kotlinx.android.synthetic.main.coba.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteTv : Fragment() {
    private lateinit var movieAdapter: CustomAdapterTvFavofite
    private lateinit var tvshowsHelper: TvshowsHelper
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieAdapter = CustomAdapterTvFavofite()
        movieAdapter.notifyDataSetChanged()
        ShowProgressDialog(false)
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        tvshowsHelper = TvshowsHelper.getInstance(this.requireContext())
        tvshowsHelper.open()
        isSavedInstanceState(savedInstanceState)


    }
    private fun isSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            loadAsync()
        } else {
            val tvshows = savedInstanceState.getParcelableArrayList<Tv>(EXTRA_STATE)
            if (tvshows != null) {
                movieAdapter.setData(tvshows)
            }
        }
    }
    private fun loadAsync() {

        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = tvshowsHelper.queryAll()
                MappingTvshowsHelper.mapCursorToArrayList(cursor)
            }
            val tvshows = deferredNotes.await()
            movieAdapter.setData(tvshows)

        }

    }
    fun ShowProgressDialog(state: Boolean){
        if(state){
            progress_bar.visibility= View.VISIBLE
        }
        else{
            progress_bar.visibility= View.GONE
        }
    }
}
