package com.example.dicoding

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dicoding.Model.Film
import kotlinx.android.synthetic.main.detail_coba.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.dicoding.db.DatabaseContract
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.example.dicoding.db.MappingMoviesHelper
import com.example.dicoding.db.MoviesHelper


class Detail : AppCompatActivity() {

    var jenis: String? = null
    var film : Film?= null
    var film1 : Film?= null
    var id : Int?=null
    var mv = MoviesHelper
    private lateinit var uriWithId: Uri
    companion object {
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_DELETE = 301
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_coba)
        film = intent.getParcelableExtra(EXTRA_POSITION)
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
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                runOnUiThread {
                    film1 = loadDBSync(film?.id.toString())
                }
            }
        }

        contentResolver?.registerContentObserver(
            CONTENT_URI,
            true,
            myObserver
        )
        film1 = loadDBSync(film?.id.toString())


        Toast.makeText(this,id.toString(), Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.love, menu)
        return super.onCreateOptionsMenu(menu)

    }
    private fun loadDBSync(id: String?): Film {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = contentResolver.query(uriWithId, null, null, null, null)

        return MappingMoviesHelper.mapCursorToList(cursor)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d("hello",mv.getInstance(this@Detail).getId(film?.id.toString()).toString())
        if (mv.getInstance(this@Detail).getId(film?.id.toString())>=1){
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
        if (mv.getInstance(this@Detail).getId(film?.id.toString())<=0){
            val values = ContentValues()
            values.put(DatabaseContract.MoviesColumn.ID, film?.id.toString())
            values.put(DatabaseContract.MoviesColumn.TITLE, film?.original_title)
            values.put(DatabaseContract.MoviesColumn.PHOTO, film?.poster_path)
            values.put(DatabaseContract.MoviesColumn.DESCRIPTION, film?.overview)
            values.put(DatabaseContract.MoviesColumn.RELEASE, film?.release_date)
            values.put(DatabaseContract.MoviesColumn.BACK, film?.backdrop_path)
            values.put(DatabaseContract.MoviesColumn.RATING, film?.vote_average)
            val result = contentResolver.insert(CONTENT_URI, values)
            if (result?.lastPathSegment?.toInt() != null) {
                setResult(RESULT_ADD, intent)
                Toast.makeText(
                    this@Detail,
                    "Favorite: " + film?.original_title,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@Detail,
                    "Failed Add Movies",
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
                    val result = contentResolver.delete(uriWithId, null, null).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, movieId)
                        setResult(RESULT_DELETE, intent)
                        Toast.makeText(
                            this@Detail,
                            "Deleted: $movieTitle",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@Detail,
                            "Failed Deleted Movies",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            reloadWidget()
            alertDialog.show()
        }
        reloadWidget()
        return super.onOptionsItemSelected(item)

    }
    private fun reloadWidget() {
        val appWidgetManager =
            AppWidgetManager.getInstance(applicationContext)
        val thisAppWidget = ComponentName(
            applicationContext.packageName,
            MoviesFavoriteWidget::class.java.name
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)

    }
}



