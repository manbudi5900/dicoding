package com.example.dicoding


import android.content.ContentValues.TAG
import android.graphics.Movie
import android.os.Bundle
import android.os.Handler
import android.provider.UserDictionary.Words.APP_ID

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.TextLanguage

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dicoding.Adapter.CustomAdapter
import com.example.dicoding.Api.APIServices
import com.example.dicoding.Api.DataRepository
import com.example.dicoding.Model.Film
import com.example.dicoding.Model.ResponseFilm
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.coba.*
import retrofit2.Call
import retrofit2.Response
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding.Model.MainViewModel
import retrofit2.Callback


class Movies : Fragment() {
    private val handler = Handler()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: CustomAdapter

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



    val list1 = ArrayList<Film>()
    private val listMovie =  MutableLiveData<ArrayList<Film>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShowProgressDialog(true)
        movieAdapter = CustomAdapter()
        movieAdapter.notifyDataSetChanged()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setMovies(getString(R.string.language))
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        mainViewModel.getMovies().observe(this, Observer { movieItems ->
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



