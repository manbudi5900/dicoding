package com.example.dicoding.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.dicoding.db.DatabaseContract.AUTHORITY
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.TABLE_NAME_MOVIE


class MovieProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MoviesHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME_MOVIE, MOVIE)
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME_MOVIE/#",
                MOVIE_ID
            )
        }
    }


    override fun getType(uri: Uri): String? {
        return null
    }


    override fun onCreate(): Boolean {
        movieHelper = MoviesHelper.getInstance(context as Context)
        movieHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            MOVIE -> movieHelper.queryAll()
            MOVIE_ID -> movieHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (MOVIE) {
            sUriMatcher.match(uri) -> movieHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        val updated: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.update(
                uri.lastPathSegment.toString(),
                contentValues
            )
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}
