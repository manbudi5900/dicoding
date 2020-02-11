package com.example.dicoding.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.dicoding.Model.Film
import com.example.dicoding.Model.Tv

internal class DatabaseTV (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val getAllTv: ArrayList<Tv>
        get() {
            val tvModelArrayList = ArrayList<Tv>()

            val selectQuery = "SELECT  * FROM $TABLE_Tv order by $KEY_ID"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    val tv = Tv()
                    tv.original_title=c.getString(c.getColumnIndex(original_title))
                    tv.backdrop_path=c.getString(c.getColumnIndex(backdrop_path))
                    tv.overview=c.getString(c.getColumnIndex(overview))
                    tv.poster_path=c.getString(c.getColumnIndex(poster_path))
                    tv.release_date=c.getString(c.getColumnIndex(release_date))
                    tv.vote_average=c.getDouble(c.getColumnIndex(vote_average))
                    tvModelArrayList.add(tv)
                } while (c.moveToNext())
            }
            return tvModelArrayList
        }

    init {

        Log.d("table", CREATE_TABLE_TV)
    }

    val getAllMovie: ArrayList<Film>
        get() {
            val movieModelArrayList = ArrayList<Film>()

            val selectQuery = "SELECT  * FROM $TABLE_Movie order by $KEY_ID"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    val tv = Film()
                    tv.original_title=c.getString(c.getColumnIndex(original_title))
                    tv.backdrop_path=c.getString(c.getColumnIndex(backdrop_path))
                    tv.overview=c.getString(c.getColumnIndex(overview))
                    tv.poster_path=c.getString(c.getColumnIndex(poster_path))
                    tv.release_date=c.getString(c.getColumnIndex(release_date))
                    tv.vote_average=c.getDouble(c.getColumnIndex(vote_average))
                    movieModelArrayList.add(tv)
                } while (c.moveToNext())
            }
            return movieModelArrayList
        }

    init {

        Log.d("table", CREATE_TABLE_TV)
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_TV)
        db.execSQL(CREATE_TABLE_Movie)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_Tv'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_Movie'")
        onCreate(db)
    }

    fun addFavorite(id:Int,original_name1: String, poster_path1 : String, overview1: String, release_date1 : String,backdrop_path1: String, vote_average1 : Double): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_ID, id)
        values.put(original_title, original_name1)
        values.put(poster_path, poster_path1)
        values.put(overview, overview1)
        values.put(release_date, release_date1)
        values.put(backdrop_path, backdrop_path1)
        values.put(vote_average,vote_average1)
        return db.insert(TABLE_Tv, null, values)
    }

    fun deleteFavorite(id: Int): Int {

        val db = this.writableDatabase
        return db.delete(TABLE_Tv,"id = ?", arrayOf(id.toString()))
    }
    fun getidTv(id:Int): Int {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_Tv Where $KEY_ID=$id"
        val cursor = db.rawQuery(selectQuery, null)
        return cursor.count

    }

    fun getidMovie(id:Int): Int {
        val db = this.writableDatabase
        val selectQuery = "SELECT * From  $TABLE_Movie Where $KEY_ID=$id"
        val cursor = db.rawQuery(selectQuery, null)
        return cursor.count
    }


    fun addFavoriteMovie(id:Int,original_name1: String, poster_path1 : String, overview1: String, release_date1 : String,backdrop_path1: String, vote_average1 : Double): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_ID, id)
        values.put(original_title, original_name1)
        values.put(poster_path, poster_path1)
        values.put(overview, overview1)
        values.put(release_date, release_date1)
        values.put(backdrop_path, backdrop_path1)
        values.put(vote_average,vote_average1)
        // insert row in students table

        return db.insert(TABLE_Movie, null, values)
    }

    fun deleteFavoriteMovie(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_Movie,"id = ?", arrayOf(id.toString()))
    }



    companion object {

        var DATABASE_NAME = "Tv_database"
        private val DATABASE_VERSION = 1
        private val TABLE_Tv = "tv"
        private val TABLE_Movie = "movie"
        private val KEY_ID = "id"
        private val poster_path = "poster_path"
        private val original_title = "original_title"
        private val overview = "overview"
        private val release_date = "release_date"
        private val backdrop_path = "backdrop_path"
        private val vote_average = "vote_average"


        private val CREATE_TABLE_TV = ("CREATE TABLE "
                + TABLE_Tv + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + original_title + " TEXT, "
                + poster_path + " TEXT, "
                + overview + " TEXT, "
                + release_date + " TEXT, "
                + backdrop_path + " TEXT, "
                + vote_average + " Double "
                +
               ");")
        private val CREATE_TABLE_Movie = ("CREATE TABLE "
                + TABLE_Movie + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + original_title + " TEXT, "
                + poster_path + " TEXT, "
                + overview + " TEXT, "
                + release_date + " TEXT, "
                + backdrop_path + " TEXT, "
                + vote_average + " Double "
                +
                ");")

    }

}