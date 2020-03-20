package com.example.consumerapp.db

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.dicoding.provider"
    const val SCHEME = "content"

    internal class MoviesColumn : BaseColumns {
        companion object {
            const val TABLE_NAME_MOVIE = "movies"
            const val ID = "_id"
            const val PHOTO = "photo"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val RATING = "rating"
            const val BACK = "back"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_MOVIE)
                .build()
        }

    }

    internal class TvshowsColumn : BaseColumns {
        companion object {
            const val TABLE_NAME_TVSHOW = "tvshows"
            const val ID = "_id"
            const val PHOTO = "photo"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val BACK = "back"
            const val RATING = "rating"
        }
    }

    fun getColumnDouble(cursor: Cursor?, columnName: String?): Double {
        return cursor?.getDouble(cursor.getColumnIndex(columnName)) ?: 0.0
    }

    fun getColumnString(cursor: Cursor?, columnName: String?): String {
        return cursor?.getString(cursor.getColumnIndex(columnName)) ?: ""
    }

    fun getColumnInt(cursor: Cursor?, columnName: String?): Int {
        return cursor?.getInt(cursor.getColumnIndex(columnName)) ?: 0
    }
}