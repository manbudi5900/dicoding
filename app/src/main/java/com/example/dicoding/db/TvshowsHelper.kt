package com.example.dicoding.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dicoding.db.DatabaseContract.TvshowsColumn.Companion.ID

class TvshowsHelper(context: Context) {
    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.TvshowsColumn.TABLE_NAME_TVSHOW
        private var INSTANCE: TvshowsHelper? = null

        fun getInstance(context: Context): TvshowsHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TvshowsHelper(context)
                    }
                }
            }
            return INSTANCE as TvshowsHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC",
            null
        )
    }

    fun queryById(id: String?): Cursor {
        return database.query(DATABASE_TABLE, null, "$ID = ?", arrayOf(id), null, null, null, null)
    }
    fun getId(id:String):Int{
        val selectQuery = "SELECT * FROM $DATABASE_TABLE Where $ID=$id"
        val cursor = database.rawQuery(selectQuery, null)
        return cursor.count
//        return database.query(DATABASE_TABLE, null, "$ID = $id", null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }

}