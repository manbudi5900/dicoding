package com.example.dicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dicoding.Model.Film
import kotlinx.android.synthetic.main.detail_coba.*
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.dicoding.db.DatabaseTV




class Detail : AppCompatActivity() {
    private var databaseHelper: DatabaseTV? = null
    var jenis: String? = null
    var film : Film?= null
    var id : Int?=null
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_coba)
        databaseHelper = DatabaseTV(this)
        film = intent.getParcelableExtra(EXTRA_PERSON)
        id = intent.getIntExtra("id",1)
        jenis = intent.getStringExtra("jenis")
        Log.d("id",id.toString())
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+ film?.backdrop_path.toString())
            .into(imageView2)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+film?.poster_path.toString())
            .into(imageView3)

        if (jenis.toString().equals("tv")){
            if (film?.id?.let { databaseHelper!!.getidTv(it) }!! >0){
                like.setText("Dislike")
            }else{
                like.setText("Like")
            }
        }else{
            if (film?.id?.let { databaseHelper!!.getidTv(it) }!!>0){
                like.setText("Dislike")
            }else{
                like.setText("Like")
            }
        }
        textView7.setText(film?.original_title.toString())
        film?.vote_average?.toFloat()?.let { rt.setRating(it) }
        desc1.setText(film?.overview)
        textView10.setText(film?.release_date)
        textView8.setText("Animation")
        rate.setText(film?.vote_average.toString())
        sinopsis.setText(resources.getString(R.string.sinopsis))
        Log.d("hello",databaseHelper!!.getidTv(id!!).toString())

        Toast.makeText(this,film?.original_title, Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.love, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (jenis.toString().equals("tv")) {
            if (film?.id?.let { databaseHelper!!.getidTv(it) }!! > 0) {
                menu?.findItem(R.id.love)?.setIcon(R.drawable.favorite)
            } else {
                menu?.findItem(R.id.love)?.setIcon(R.drawable.nonfavorite)
            }
        }else{
            if (film?.id?.let { databaseHelper!!.getidMovie(it) }!! > 0) {
                menu?.findItem(R.id.love)?.setIcon(R.drawable.favorite)
            } else {
                menu?.findItem(R.id.love)?.setIcon(R.drawable.nonfavorite)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.love) {
            if (jenis.toString().equals("tv")) {
                if (film?.id?.let { databaseHelper!!.getidTv(it) }!!>=0) {
                    removeFromFavorite()
                    item.setIcon(R.drawable.nonfavorite)
                }else{
                    addToFavorite()
                    item.setIcon(R.drawable.favorite)
                }
            }else{
                if (film?.id?.let { databaseHelper!!.getidMovie(it) }!!>=1) {
                    removeFromFavorite()
                    item.setIcon(R.drawable.nonfavorite)
                }else{
                    Log.d("wew",film?.id?.let { databaseHelper!!.getidMovie(it) }!!.toString())
                    addToFavorite()
                    item.setIcon(R.drawable.favorite)
                }
            }

        }

        return super.onOptionsItemSelected(item)
    }
    private fun addToFavorite() {
        if (jenis.toString().equals("tv")) {
            id?.let {
                databaseHelper!!.addFavorite(
                    it,film?.original_title.toString(),
                    film?.poster_path.toString(),
                    film?.overview.toString(),
                    film?.release_date.toString(),
                    film?.backdrop_path.toString(),
                    film?.vote_average!!.toDouble())
            }
            Toast.makeText(this, "Stored Successfully!", Toast.LENGTH_SHORT).show()
        }else{
            id?.let {
                databaseHelper!!.addFavoriteMovie(
                    it,film?.original_title.toString(),
                    film?.poster_path.toString(),
                    film?.overview.toString(),
                    film?.release_date.toString(),
                    film?.backdrop_path.toString(),
                    film?.vote_average!!.toDouble())
            }
            Toast.makeText(this, "Stored Successfully!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun removeFromFavorite() {
        if (jenis.toString().equals("tv")) {
            id?.let { databaseHelper!!.deleteFavorite(it) }
        }else{
            id?.let { databaseHelper!!.deleteFavoriteMovie(it) }
        }

    }


}



