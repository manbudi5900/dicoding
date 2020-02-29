package com.example.dicoding

import android.database.ContentObserver
import android.os.Bundle
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding.Adapter.CustomAdapterMovieFavofite
import com.example.dicoding.Model.Film
import com.example.dicoding.Model.MainViewModel
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.example.dicoding.db.MappingMoviesHelper
import kotlinx.android.synthetic.main.coba.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager

class FavoriteMovie : Fragment() {
    private lateinit var movieAdapter: CustomAdapterMovieFavofite
    private lateinit var mainViewModel: MainViewModel
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ShowProgressDialog(false)
        movieAdapter = CustomAdapterMovieFavofite()
        movieAdapter.notifyDataSetChanged()
//        databaseHelper = this.context?.let { DatabaseTV(it) }!!
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
//        TvArrayList = databaseHelper!!.getAllMovie
//        mainViewModel.setMovies(TvArrayList)
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                activity?.runOnUiThread {
                    loadAsync()
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)
        isSavedInstanceState(savedInstanceState)
    }
    private fun isSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            loadAsync()
        } else {
            val movies = savedInstanceState.getParcelableArrayList<Film>(EXTRA_STATE)
            if (movies != null) {
                movieAdapter.setData(movies)
//                movieAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun loadAsync() {

        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingMoviesHelper.mapCursorToArrayList(cursor)
            }
            val movies = deferredMovies.await()
            Log.d("sa",movies.toString())
            movieAdapter.setData(movies)

//            ShowProgressDialog(false)
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
