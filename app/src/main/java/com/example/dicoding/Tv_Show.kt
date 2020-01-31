package com.example.dicoding

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding.Adapter.CustomAdapter
import com.example.dicoding.Adapter.CustomAdapterTv
import com.example.dicoding.Api.APIServices
import com.example.dicoding.Api.DataRepository
import com.example.dicoding.Model.Film
import com.example.dicoding.Model.MainViewModel
import com.example.dicoding.Model.ResponseFilm
import kotlinx.android.synthetic.main.coba.*
import retrofit2.Call
import retrofit2.Response

class Tv_Show : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: CustomAdapterTv

    private val genre = arrayOf(
        "Action",
        "Action",
        "Adventure",
        "Adventure",
        "Petualangan",
        "Action",
        "Action",
        "Animation",
        "Komedi",
        "Horor")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ShowProgressDialog(true)
        movieAdapter = CustomAdapterTv()
        movieAdapter.notifyDataSetChanged()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setTVShows(getString(R.string.language))
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        mainViewModel.getTVShows().observe(this, Observer { movieItems ->
            if(movieItems!=null){
                Log.d("kakakakaakkak","he")
                movieAdapter.setData(movieItems)
                movieAdapter.notifyDataSetChanged()
                ShowProgressDialog(false)
            }
        })


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
