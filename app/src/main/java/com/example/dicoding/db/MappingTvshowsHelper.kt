package com.example.dicoding.db

import android.database.Cursor
import com.example.dicoding.Model.Tv

object MappingTvshowsHelper {
    fun mapCursorToArrayList(tvshowsCursor: Cursor): ArrayList<Tv> {
        val tvshowList = ArrayList<Tv>()
        while (tvshowsCursor.moveToNext()) {
            val id =
                tvshowsCursor.getInt(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.ID))
            val photo =
                tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.PHOTO))
            val title =
                tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.TITLE))
            val description =
                tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.DESCRIPTION))
            val release =
                tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.RELEASE))
            val back =
                tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.BACK))
            val rating =
                tvshowsCursor.getDouble(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.RATING))
            tvshowList.add(Tv(id, title, photo, description, release, back, rating))
        }
        return tvshowList
    }

    fun mapCursorToList(moviesCursor: Cursor?): Tv {
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
                    moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.TvshowsColumn.BACK))
                val rating =
                    moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MoviesColumn.RATING))

                return Tv(id, title, photo, description, release, back, rating)
            } else
                return Tv(null, null, null, null, null, null, 0.0)

        } else {
            return Tv(null, null, null, null, null, null, 0.0)
        }
    }

}