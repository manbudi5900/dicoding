package com.example.dicoding

import android.database.Cursor
import com.example.dicoding.db.DatabaseContract.getColumnDouble
import com.example.dicoding.db.DatabaseContract.getColumnString
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.DESCRIPTION
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.PHOTO
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.RATING
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.RELEASE
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.TITLE
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.ID
import com.example.dicoding.db.DatabaseContract.MoviesColumn.Companion.BACK


class ResultsItem(cursor: Cursor?) {
    var id: String? = null
    var photo: String? = null
    var title: String? = null
    var description: String? = null
    var release: String? = null
    var rating: Double = 0.0
    var backdrop_path1 : String? = null

    init {
        id = getColumnString(cursor, ID)
        photo = getColumnString(cursor, PHOTO)
        title = getColumnString(cursor, TITLE)
        description = getColumnString(cursor, DESCRIPTION)
        release = getColumnString(cursor, RELEASE)
        rating = getColumnDouble(cursor, RATING)
        backdrop_path1 = getColumnString(cursor, BACK)
    }

    override fun toString(): String {
        return """ResultsItem{description = '$description',title = '$title',photo = '$photo',release = '$release',id = '$id'}"""
    }
}

