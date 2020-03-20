package com.example.consumerapp.db

import android.database.Cursor
import com.example.consumerapp.model.Film


object MappingMoviesHelper {
    fun mapCursorToArrayList(moviesCursor: Cursor?): ArrayList<Film> {
        val moviesList = ArrayList<Film>()
        if (moviesCursor != null) {
            while (moviesCursor.moveToNext()) {
                val id =
                    moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.ID))
                val photo =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.PHOTO))
                val title =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.TITLE))
                val description =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.DESCRIPTION))
                val release =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.RELEASE))
                val back =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.BACK))
                val rating =
                    moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.RATING))
                moviesList.add(Film(id,  title, photo, description, release, back, rating))
            }
        }
        return moviesList
    }

    fun mapCursorToList(moviesCursor: Cursor?): Film{
        if (moviesCursor != null) {
            if (moviesCursor.moveToFirst()) {
                val id =
                    moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.ID))
                val photo =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.PHOTO))
                val title =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.TITLE))
                val description =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.DESCRIPTION))
                val release =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.RELEASE))
                val back =
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.BACK))
                val rating =
                    moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.RATING))

                return Film(id, title, photo, description, release,back, rating)
            } else
                return Film(null, null, null, null, null,null, 0.0)
        } else
            return Film(null, null, null, null, null,null, 0.0)
    }
}