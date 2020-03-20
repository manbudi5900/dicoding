package com.example.dicoding.Api


import com.example.dicoding.Model.ResponseFilm
import com.example.dicoding.Model.ResponseTv
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {


    @GET("3/search/movie")
    fun getMovies(@Query("api_key")key:String,
                  @Query("language")lang:String,
                  @Query("query")query:String): Call<ResponseFilm>

    @GET("3/search/tv")
    fun getTv(@Query("api_key")key:String,
              @Query("language")lang:String,
              @Query("query")query:String): Call<ResponseTv>

}