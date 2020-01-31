package com.example.dicoding.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DataRepository {
    companion object {
        fun getClient() : Retrofit {
            val BASE_URL = "https://api.themoviedb.org/"
            val retrofit: Retrofit = Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}