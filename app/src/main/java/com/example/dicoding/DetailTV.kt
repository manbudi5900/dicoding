package com.example.dicoding

import android.content.ContentValues
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.dicoding.Model.Film
import com.example.dicoding.Model.Tv
import com.example.dicoding.db.DatabaseContract
import com.example.dicoding.db.MappingTvshowsHelper
import com.example.dicoding.db.TvshowsHelper
import kotlinx.android.synthetic.main.detail_coba.*

class DetailTV : AppCompatActivity() {
    var jenis: String? = null
    var film : Tv?= null
    var film1 : Tv?= null
    var itm : Menu?=null
    var id : Int?=null
    private lateinit var mv: TvshowsHelper
    private lateinit var uriWithId: Uri
    companion object {
        const val OBJECT_TVSHOW = "object_tv"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_coba)
        mv = TvshowsHelper.getInstance(applicationContext)
        mv.open()
        film = intent.getParcelableExtra(OBJECT_TVSHOW) as Tv
        id = film?.id
        jenis = intent.getStringExtra("jenis")
//        Log.d("id",id.toString())
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+ film?.backdrop_path.toString())
            .into(imageView2)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original"+film?.poster_path.toString())
            .into(imageView3)

        textView7.setText(film?.original_title.toString())
        film?.vote_average?.toFloat()?.let { rt.setRating(it) }
        desc1.setText(film?.overview)
        textView10.setText(film?.release_date)
        textView8.setText("Animation")
        rate.setText(film?.vote_average.toString())
        sinopsis.setText(resources.getString(R.string.sinopsis))
        film1 = loadDBSync(film?.id.toString())
        Toast.makeText(this,id.toString(), Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.love, menu)
        return super.onCreateOptionsMenu(menu)

    }
    private fun loadDBSync(id: String?): Tv {
        val cursor = mv.queryById(id)

        return MappingTvshowsHelper.mapCursorToList(cursor)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d("hello",mv.getId(film?.id.toString()).toString())
        if (mv.getId(film?.id.toString())>=1){
            if (menu != null) {
                menu.findItem(R.id.love)?.icon =
                    ContextCompat.getDrawable(this, R.drawable.favorite)
            }
        }else{
            if (menu != null) {
                menu.findItem(R.id.love)?.icon =
                    ContextCompat.getDrawable(this, R.drawable.nonfavorite)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.love) {
            val values = ContentValues()
            values.put(DatabaseContract.TvshowsColumn.ID, film?.id.toString())
            values.put(DatabaseContract.TvshowsColumn.TITLE, film?.original_title)
            values.put(DatabaseContract.TvshowsColumn.PHOTO, film?.poster_path)
            values.put(DatabaseContract.TvshowsColumn.DESCRIPTION, film?.overview)
            values.put(DatabaseContract.TvshowsColumn.RELEASE, film?.release_date)
            values.put(DatabaseContract.TvshowsColumn.BACK, film?.backdrop_path)
            values.put(DatabaseContract.TvshowsColumn.RATING, film?.vote_average)
            val result = mv.insert(values)
            if (result > 0) {
                setResult(DetailTV.RESULT_ADD, intent)
                Toast.makeText(
                    this@DetailTV,
                    "Favorite: " + film?.original_title,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@DetailTV,
                    "Failed Add Tv",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            val movieId = film?.id
            val movieTitle = film?.original_title

            val dialogTitle = "Delete Favorite Movies"
            val dialogMessage =
                "Are you sure delete movie $movieTitle?"
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.MyAlertDialog)

            alertDialogBuilder.setTitle(dialogTitle)
            alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val result = mv.deleteById(film?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(DetailTV.EXTRA_POSITION, movieId)
                        setResult(DetailTV.RESULT_DELETE, intent)
                        Toast.makeText(
                            this@DetailTV,
                            "Deleted: $movieTitle",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailTV,
                            "Failed Deleted tv",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
//        reloadWidget()
        return super.onOptionsItemSelected(item)
    }
}




