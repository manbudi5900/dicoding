package com.example.dicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dicoding.Model.Film
import kotlinx.android.synthetic.main.detail_coba.*


class Detail : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_coba)
        val film = intent.getParcelableExtra(EXTRA_PERSON) as Film
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+film.backdrop_path.toString())
            .into(imageView2)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+film.poster_path.toString())
            .into(imageView3)


        textView7.setText(film.original_title.toString())
        film.vote_average?.toFloat()?.let { rt.setRating(it) }
        desc1.setText(film.overview)
        textView10.setText(film.release_date)
        textView8.setText("Animation")
        rate.setText(film.vote_average.toString())
        sinopsis.setText(resources.getString(R.string.sinopsis))
        Toast.makeText(this,film.original_title, Toast.LENGTH_SHORT).show()
    }
}



