package com.example.dicoding.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.TABLE_NAME_MOVIE
import com.example.dicoding.db.DatabaseContract.TvshowsColumn.Companion.TABLE_NAME_TVSHOW

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        private const val DATABASE_NAME = "moviecatalogue"

        private const val DATABASE_VERSION = 2

        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_NAME_MOVIE" +
                " (${DatabaseContract.MoviesColumn.ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.MoviesColumn.TITLE} TEXT ," +
                " ${DatabaseContract.MoviesColumn.PHOTO} TEXT ," +
                " ${DatabaseContract.MoviesColumn.DESCRIPTION} TEXT ," +
                " ${DatabaseContract.MoviesColumn.RELEASE} TEXT ," +
                " ${DatabaseContract.MoviesColumn.BACK} TEXT," +
                " ${DatabaseContract.MoviesColumn.RATING} REAL )"

        private val SQL_CREATE_TABLE_TVSHOW = "CREATE TABLE $TABLE_NAME_TVSHOW" +
                " (${DatabaseContract.TvshowsColumn.ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.TvshowsColumn.TITLE} TEXT ," +
                " ${DatabaseContract.TvshowsColumn.PHOTO} TEXT ," +
                " ${DatabaseContract.TvshowsColumn.DESCRIPTION} TEXT ," +
                " ${DatabaseContract.TvshowsColumn.RELEASE} TEXT ," +
                " ${DatabaseContract.MoviesColumn.BACK} TEXT," +
                " ${DatabaseContract.TvshowsColumn.RATING} REAL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TVSHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_MOVIE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_TVSHOW")
        onCreate(db)
    }
}