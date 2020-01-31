package com.example.dicoding.Model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding.Api.APIServices
import com.example.dicoding.Api.DataRepository
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val listMovie =  MutableLiveData<ArrayList<Film>>()
    private val listTV =  MutableLiveData<ArrayList<Tv>>()
    val apiInterface : APIServices = DataRepository.getClient().create(APIServices::class.java)

    fun setMovies(lang: String){
        val dataMovie = ArrayList<Film>()
        val call : Call<ResponseFilm> = apiInterface.getMovies("7b3ad7574fb018c27ba6b1ec6da5c6e0",lang)
        call.enqueue(object : retrofit2.Callback<ResponseFilm> {
            override fun onFailure(call: Call<ResponseFilm>, t: Throwable) {
                Log.d("${ContentValues.TAG}", "Gagal Fetch Popular Movie")
            }
            override fun onResponse(call: Call<ResponseFilm>, response: Response<ResponseFilm>) {
                if (response != null) {
                    val data = response.body()
                    dataMovie.addAll(data!!.results)
                    listMovie.postValue(dataMovie)
                }
            }
        })
    }

    fun setTVShows(lang: String){
        val dataTv = ArrayList<Tv>()
        val call : Call<ResponseTv> = apiInterface.getTv("7b3ad7574fb018c27ba6b1ec6da5c6e0",lang)
        call.enqueue(object : retrofit2.Callback<ResponseTv> {
            override fun onFailure(call: Call<ResponseTv>, t: Throwable) {
                Log.d("${ContentValues.TAG}", "Gagal Fetch Popular Movie")
            }
            override fun onResponse(call: Call<ResponseTv>, response: Response<ResponseTv>) {
                if (response != null) {
                    val data = response.body()
                    Log.d("ResponeLog", data!!.toString())
                    dataTv.addAll(data!!.results)
                    listTV.postValue(dataTv)
                }
            }
        })
    }

    fun getMovies(): LiveData<ArrayList<Film>>{
        return listMovie
    }

    fun getTVShows(): LiveData<ArrayList<Tv>>{
        return listTV
    }

}